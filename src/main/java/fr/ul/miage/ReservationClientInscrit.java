package fr.ul.miage;

import fr.ul.miage.Enums.ETypeImmat;
import fr.ul.miage.dtos.ImmatriculationDto;
import fr.ul.miage.dtos.ReservationDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static fr.ul.miage.Outils.checkYesOrNoAnswer;
import static fr.ul.miage.Outils.miseEnFormeImmat;

public class ReservationClientInscrit {

    public ReservationDto procedureInscription() {
        Scanner scanner = new Scanner(System.in);
        ImmatriculationDto plaqueChoisie = null;

        System.out.println("RESERVER : ");
        //TODO : Remplacer cette fonction bidon par la vraie vérification de dispo de bornes
        List<Integer> bornesDispo = mockListeBornesDispo();
        if(bornesDispo.isEmpty()) {
            System.out.println("Il n'y a pas de bornes disponibles actuellement, veuilles réessayer plus tard.");
            return null;
        }
        int selectedBorneId = bornesDispo.getFirst();
        System.out.println("La borne numéro " + selectedBorneId + " est disponible pour ce créneau.\n");
        System.out.println("Souhaitez-vous utiliser un de vos véhicules enregistrés ?\n" +
                "O - Oui / N - Non");
        String choix = scanner.nextLine();
        while(!checkYesOrNoAnswer(choix)) {
            System.out.println("""
                    Je n'ai pas compris votre choix.\s
                    Souhaitez-vous utiliser un de vos véhicules enregistrés ?\s
                    O - Oui / N - Non
                    """);
            choix = scanner.nextLine();
        }
        if(choix.equalsIgnoreCase("o") || choix.equalsIgnoreCase("oui")) {
            //TODO: Remplacer la méthode mock par une méthode qui va chercher les plaques d'un client (uniquement les normales).
            List<ImmatriculationDto> immatriculationDtos = mockListeImmatsClient();
            if (!immatriculationDtos.isEmpty()) {
                System.out.println(afficherPlaquesImmatriculation(immatriculationDtos));
                // on récupère la plaque
                int indexPlaque = Outils.saisirInt(0, immatriculationDtos.size());
                plaqueChoisie =  immatriculationDtos.get(indexPlaque - 1);
            } else {
                System.out.println("Vous n'avez aucun véhicule enregistré dans votre compte. \n" +
                        "Souhaitez-vous utiliser une plaque temporaire ou abandonner la réservation ? \n" +
                        "1 - Ajouter une plaque temporaire | 2 - Abandonner ");
                int option = Outils.saisirInt(0,2);
                if(option == 1) {
                    plaqueChoisie = ajouterPlaqueTemporaire(scanner);

                }
            }

        }
        if(choix.equalsIgnoreCase("n") || choix.equalsIgnoreCase("non")) {
            plaqueChoisie = ajouterPlaqueTemporaire(scanner);
        }
        return plaqueChoisie != null ?
                new ReservationDto(
                        plaqueChoisie.getIdPlaque(),
                        selectedBorneId,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusHours(1),
                        plaqueChoisie.getTypeImmat(),
                        0
                ) :
                null;
    }

    private ImmatriculationDto ajouterPlaqueTemporaire(Scanner scanner) {
        System.out.println("Ajout de plaque d'immatriculation temporaire : ");
        String plaque = Outils.recupererPlaqueImmat(scanner);
        //TODO il faut voir comment ajouter ça en base, sachant qu'il faut lier la plaque au client une fois insérée.
        return new ImmatriculationDto(plaque, ETypeImmat.TEMPORAIRE);


    }
    private String afficherPlaquesImmatriculation(List<ImmatriculationDto> listeImmat) {
        StringBuilder sb = new StringBuilder();
        sb.append("Liste des immatriculations : ");
        for(int i = 0; i < listeImmat.size(); i++) {
            sb.append(i+1);
            sb.append(" - ");
            sb.append(miseEnFormeImmat(listeImmat.get(i).getPlaque()));
        }
        sb.append("\n");
        return sb.toString();
    }

     private List<Integer> mockListeBornesDispo() {
         return new ArrayList<>(List.of(1,2,3,4));
     }

     public List<ImmatriculationDto> mockListeImmatsClient() {
         return List.of(
                new ImmatriculationDto(44, "AA111BB", ETypeImmat.NORMALE),
                new ImmatriculationDto(62, "CC222DD", ETypeImmat.NORMALE)
        );
    }


}
