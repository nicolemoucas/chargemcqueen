package fr.ul.miage;

import fr.ul.miage.Enums.ETypeImmat;
import fr.ul.miage.Enums.ETypeReservation;
import fr.ul.miage.dtos.ClientDto;
import fr.ul.miage.dtos.ImmatriculationDto;
import fr.ul.miage.dtos.ReservationDto;

import java.awt.*;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static fr.ul.miage.Outils.checkYesOrNoAnswer;
import static fr.ul.miage.Outils.miseEnFormeImmat;

/**
 * Classe qui permet de gérer la création de réservations pour les clients inscrits.
 */
public class ReservationClientInscrit {

    /**
     * La procédure de réservation.
     *
     * @param outilsBaseSQL
     * @param currentlyConnectedClient
     * @return l'objet réservation créé à l'issue de la procédure. Null si aucune borne n'est disponible ou si la procédure a été abanadonnée.
     */
    public ReservationDto procedureReservation(OutilsBaseSQL outilsBaseSQL, ClientDto currentlyConnectedClient) {
        Scanner scanner = new Scanner(System.in);

        String debut = dateActuelleFormate();
        String fin = finCreneau(debut);

        System.out.println("RESERVER : ");
        ResultSet res = this.getBornesDisponibles(debut, fin, outilsBaseSQL);
        //List<Integer> bornesDispo = mockListeBornesDispo();
        List<Integer> bornesDispo = new ArrayList<Integer>();
        try {
            while (res.next()) {
                bornesDispo.add(res.getInt("idBorne"));
            }
        } catch (Exception e){
            System.out.println(e);
        }

        if (bornesDispo.isEmpty()) {
            System.out.println("Il n'y a pas de bornes disponibles actuellement, veuillez réessayer plus tard.");
            return null;
        }

        int selectedBorneId = selectBorne(bornesDispo);
        ImmatriculationDto plaqueChoisie = choisirPlaque(scanner, outilsBaseSQL, currentlyConnectedClient);

        // si on n'a pas choisi de plaque valable, on va abandonner la réservation.
        if (plaqueChoisie == null) {
            System.out.println("Reservation abandonnée.");
            return null;
        }

        int idRes = this.creerReservationBDD(plaqueChoisie, selectedBorneId, debut, fin, "unique", outilsBaseSQL);
        ReservationDto reservationDto = creerReservation(plaqueChoisie, selectedBorneId, idRes);
        afficherRecapReservation(plaqueChoisie, reservationDto);
        return reservationDto;
    }

    /**
     * La méthode revoie les bornes disponibles pour une période donnée
     * Une date doit être saisie au format 'AAAA-MM-DD HH:MM:SS' ou 'AAAA-MM-DD HH:MM'
     *
     * @return listes des bornes disponibles
     */
    private ResultSet getBornesDisponibles(String dateDeb, String dateFin, OutilsBaseSQL outilsBaseSQL) {

        System.out.println(dateDeb);
        System.out.println(dateFin);
        String query = "Select * \n" +
                "from bornerecharge br\n" +
                "left join reservation res\n" +
                "on br.idborne = res.idborne\n" +
                "where br.etatBorne = 'disponible' \n" +
                " AND ( (res.idReservation IS NULL) OR (res.heureDebut > '" + dateFin + "' OR res.heureFin < '" + dateDeb + "'));";
        String erreur = "Une erreur s'est produite lors de la recherche des bornes disponibles !";
        return outilsBaseSQL.rechercheSQL(query, erreur);
    }

    private int creerReservationBDD(ImmatriculationDto plaque, int idBorne, String deb, String fin, String type, OutilsBaseSQL outilsBaseSQL){
        System.out.println("creerReservationBDD");
        String query = "INSERT INTO Reservation (numeroImmat, idBorne, heureDebut, heureFin, type, nbProlongations)\n" +
                " VALUES ('"+ plaque.getPlaque() +"', "+ idBorne +", '"+ deb +"', '"+ fin +"', '"+ type +"', 0)";
        System.out.println(query);
        String erreur = "Une erreur s'est produite lors de la réservation !";
        int res = outilsBaseSQL.majSQL(query, erreur);
        System.out.println(res);
        return res;
    }

    /**
     * La méthode permet de récupérer la date et heure actuelle
     *
     * @return date au format 'AAAA-MM-DD HH:MM'
     */
    private String dateActuelleFormate(){
        String resultat = "";
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        resultat = now.format(formatter);
        return resultat;
    }

    /**
     * Ajoute 2 heures au début pour obtenir un créneau
     *
     * @param debutCrenau : prend une date au format 'AAAA-MM-DD HH:MM'
     * @return date au format 'AAAA-MM-DD HH:MM'
     */
    private String finCreneau(String debutCrenau){
        String resultat = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(debutCrenau, formatter);
        LocalDateTime futureDateTime = dateTime.plusHours(1);
        resultat = futureDateTime.format(formatter);
        return resultat;
    }


    /**
     * Méthode utilisée pour choisir si on veut utiliser un véhicule pré-enregistré ou un véhicule de location.
     *
     * @param scanner le scanner utilisé pour écouter les inputs utilisateur.
     * @param outilsBaseSQL : l'outils pour les requêtes SQL
     * @param currentlyConnectedClient : le client qui fait l'action
     * @return {ImmatriculationDto} la plaque d'immatriculation sélectionnée.
     */
    private ImmatriculationDto choisirPlaque(Scanner scanner, OutilsBaseSQL outilsBaseSQL, ClientDto currentlyConnectedClient) {
        System.out.println("Souhaitez-vous utiliser un de vos véhicules enregistrés ?\n" +
                "O - Oui / N - Non");
        String choix = scanner.nextLine();

        while (!checkYesOrNoAnswer(choix)) {
            System.out.println("""
                    Je n'ai pas compris votre choix.\s
                    Souhaitez-vous utiliser un de vos véhicules enregistrés ?\s
                    O - Oui / N - Non
                    """);
            choix = scanner.nextLine();
        }

        if (choix.equalsIgnoreCase("o") || choix.equalsIgnoreCase("oui")) {
            return choisirPlaqueEnregistree(scanner, outilsBaseSQL, currentlyConnectedClient);
        } else if (choix.equalsIgnoreCase("n") || choix.equalsIgnoreCase("non")) {
            return ajouterPlaqueTemporaire(scanner, outilsBaseSQL, currentlyConnectedClient);
        }

        return null;
    }

    private ImmatriculationDto choisirPlaqueEnregistree(Scanner scanner, OutilsBaseSQL outilsBaseSQL, ClientDto currentlyConnectedClient) {
        //List<ImmatriculationDto> immatriculationDtos = mockListeImmatsClient();

        List<ImmatriculationDto> listePlaque = new ArrayList<>();
        String query = "Select Immatriculation.numeroImmat, Immatriculation.typeImmat from utilise " +
                "inner join Immatriculation " +
                "on utilise.numeroImmat = Immatriculation.numeroImmat " +
                "where idClient = " + currentlyConnectedClient.getIdClient() + " ;";
        String erreur = "Une erreur avec la recherche des plaque du client est arrivé";
        ResultSet plaques = outilsBaseSQL.rechercheSQL(query, erreur);

        try {
            while (plaques.next()) {
                listePlaque.add(new ImmatriculationDto(plaques.getString("numeroImmat"), ETypeImmat.valueOf(plaques.getString("typeImmat"))));
            }
        } catch (Exception e){
            System.out.println(e);
        }

        if (!listePlaque.isEmpty()) {
            System.out.println(afficherPlaquesImmatriculation(listePlaque));
            int indexPlaque = Outils.saisirInt(0, listePlaque.size());
            return listePlaque.get(indexPlaque - 1);
        } else {
            System.out.println("Vous n'avez aucun véhicule enregistré dans votre compte. \n" +
                    "Souhaitez-vous utiliser une plaque temporaire ou abandonner la réservation ? \n" +
                    "1 - Ajouter une plaque temporaire | 2 - Abandonner ");
            int option = Outils.saisirInt(0, 2);
            if (option == 1) {
                return ajouterPlaqueTemporaire(scanner, outilsBaseSQL, currentlyConnectedClient);
            }
        }

        return null;
    }

    /**
     * Méthode utilisée pour construire l'objet RéservationDto à partir des informations récupérées lors de la procédure d'inscription.
     *
     * @param plaqueChoisie la plaque d'immatriculation du véhicule concerné par la recharge.
     * @param selectedBorneId l'id de la borne que el client pourra utiliser.
     * @param idres l'id de la Réservation
     * @return
     */
    private ReservationDto creerReservation(ImmatriculationDto plaqueChoisie, int selectedBorneId, int idres) {
        return new ReservationDto(
                plaqueChoisie.getPlaque(),
                selectedBorneId,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                ETypeReservation.UNIQUE,
                0,
                idres
        );
    }

    /**
     * Méthode utilisée pour afficher un récapitulatif de la réservation qui vient d'être faite.
     *
     * @param plaqueChoisie la plaque d'immatriculation du véhicule concerné par la recharge.
     * @param reservationDto la réservation effectuée.
     */
    private void afficherRecapReservation(ImmatriculationDto plaqueChoisie, ReservationDto reservationDto) {
        System.out.println("Résumé de la reservation : ");
        System.out.println("Votre véhicule : " + miseEnFormeImmat(plaqueChoisie.getPlaque()));
        System.out.println("Le numéro de votre borne : " + reservationDto.getIdBorne());
        System.out.println("L'heure de début de votre recharge : " + reservationDto.getHeureDebut().format(Outils.FORMAT_DATES_RESERVATIONS));
        System.out.println("L'heure de fin de votre recharge : " + reservationDto.getHeureFin().format(Outils.FORMAT_DATES_RESERVATIONS));
        System.out.println("L'identification de réservation de votre recharge : " + reservationDto.getIdReservation());
    }

    /**
     * Méthode utilisée pour récupérer la première borne disponible.
     *
     * @param bornesDispo la liste de bornes disponibles.
     * @return {int} l'id de la première borne disponible.
     */
    private int selectBorne(List<Integer> bornesDispo) {
        int selectedBorneId = bornesDispo.get(0);
        System.out.println("La borne numéro " + selectedBorneId + " est disponible pour ce créneau.\n");
        return selectedBorneId;
    }

    /**
     * Méthode utilisée pour ajouter une plaque d'immatriculation temporaire (pour les voitures de location par exemple).
     *
     * @param scanner le scanner utilisé pour récupérer les inputs utilisateur.
     * @return {ImmatriculationDto} la plaque d'immatriculation
     */
    private ImmatriculationDto ajouterPlaqueTemporaire(Scanner scanner, OutilsBaseSQL outilsBaseSQL, ClientDto currentlyConnectedClient) {
        System.out.println("Ajout de plaque d'immatriculation temporaire : ");
        String plaque = Outils.recupererPlaqueImmat(scanner);

        // Tester si existe en BDD
        String query = "SELECT * FROM immatriculation WHERE numeroimmat = '" + plaque + "' ;";
        String erreur = "Pas de plaque " + plaque ;
        ResultSet recherche = outilsBaseSQL.rechercheSQL(query, erreur);
        try {
            if (!recherche.next()) {
                // Si non, créer
                query = "INSERT INTO Immatriculation (numeroimmat, typeImmat) VALUES ( '" + plaque + "', 'Temporaire');";
                erreur = "Problème de création de la plaque";
                int insert = outilsBaseSQL.majSQL(query, erreur);
                if(insert < 0){
                    System.out.println("Plaque crée !");
                }
            }
        } catch (Exception e){
           // System.out.println(e);
        }

        // Tester si client utilise
        query = "Select * from Utilise where Utilise.numeroImmat = '" + plaque + "' AND idClient = " + currentlyConnectedClient.getIdClient() + " ;";
        erreur = "Erreur dans la recherche des plaques";
        recherche = outilsBaseSQL.rechercheSQL(query, erreur);
        try {
            if (!recherche.next()) {
                // Si non, utilise
                query = "INSERT INTO Utilise (idClient, numeroImmat) VALUES (" + currentlyConnectedClient.getIdClient() + ", " + plaque + ");";
                erreur = "Problème de création de l'utilisation";
                int insert = outilsBaseSQL.majSQL(query, erreur);
                if(insert < 0){
                    System.out.println("Utilisation ajoutée !");
                }
            }
        } catch (Exception e){
            System.out.println(e);
        }

        return new ImmatriculationDto(plaque, ETypeImmat.TEMPORAIRE);
    }

    /**
     * Méthode utilisée pour afficher une liste de plaques d'immatriculation.
     *
     * @param listeImmat la liste de plaques d'immatriculation à afficher.
     * @return {String} la liste mise en forme.
     */
    private String afficherPlaquesImmatriculation(List<ImmatriculationDto> listeImmat) {
        StringBuilder sb = new StringBuilder();
        sb.append("Liste des immatriculations : \n");
        for(int i = 0; i < listeImmat.size(); i++) {
            sb.append(i+1);
            sb.append(" - ");
            sb.append(miseEnFormeImmat(listeImmat.get(i).getPlaque()));
            sb.append("\n");
        }
        return sb.toString();
    }

}
