import java.util.ArrayList;
import java.util.Scanner;
import java.text.DecimalFormat;
import java.time.LocalDate;

public class App {
    public static ArrayList<LifeCycle> entries = new ArrayList<LifeCycle>();
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        String selection;
        boolean sentinel = false;
        int numSelect = 0;
        //System.out.println(LocalDate.now().compareTo(LocalDate.of(2023, 12, 19)));
        //Title Screen loop
        while (!sentinel) {
            //display existing lifecycles
            welcome();
            selection = input.nextLine();
            if (selection.equalsIgnoreCase("new")){
                LifeCycle current = createLifeCycle(input);
                if (current != null) {
                    entries.add(current);
                    System.out.println("\nYou have entered the following life cycle: ");
                    displayLifeCycle(entries.get(entries.size() - 1));
                }
            }
            else if (Integer.parseInt(selection) <= entries.size() && Integer.parseInt(selection) > 0) {
                int cycleIndex = Integer.parseInt(selection) - 1;
                System.out.println("You selected:");
                while (numSelect != 4) {
                    //please remember to update this (above) value when adding more options
                    displayLifeCycle(entries.get(cycleIndex));
                    System.out.println("\nWhat would you like to do?\n");
                    System.out.println("1) Get nutrient amounts");
                    System.out.println("2) Update growth stage");
                    System.out.println("3) Delete life cycle");
                    System.out.println("4) Back to menu");
                    //Other ideas: update growth stage (initiate bloom phase), edit lifecycle, add notes, view lifecycle history (with notes), BE SURE TO CHANGE ENFORCEMENTS IF ADDING
                    numSelect = Integer.parseInt(input.nextLine());
                    while (numSelect > 4 || numSelect < 1) {
                        System.out.println("Invalid entry\n");
                        System.out.println("1) Get nutrient amounts");
                        System.out.println("2) Update growth stage");
                        System.out.println("3) Delete life cycle");
                        System.out.println("4) Back to menu");
                        numSelect = Integer.parseInt(input.nextLine());
                    }
                    if (numSelect == 1) {
                        getNutrientInfo(entries.get(cycleIndex), input);
                    }
                    else if (numSelect == 2) {
                        initiateBloom(entries.get(cycleIndex), input);
                    }
                    else if (numSelect == 3) {
                        //Need to add a double/triple check "Are you sure you would like to delete temp.getName()? Note: this action cannot be undone."
                        LifeCycle temp = entries.get(cycleIndex);
                        entries.remove(cycleIndex);
                        System.out.println("You have removed " + temp.getName() + " from the index.");
                        //*Update this enforcement if changing options - back to main menu
                        numSelect = 4;
                    }
                }
            }
            else if (Integer.parseInt(selection) == -1) {
                sentinel = true;
            }
            else {
                System.out.println("Invalid entry");
            }
        }
        System.out.println("Program has ended.");





        //please enter the name of the lifecycle
        //enforce that the same name cannot be entered twice
    }

    public static void welcome() {
        int count = 0;
        System.out.println("Hydroponic Helper\n");
        if (entries.isEmpty()) {
            System.out.println("No entered life cycles...");
        }
        for (LifeCycle entry : entries) {
            System.out.println(++count + ") " + entry.getName());
        }
        System.out.println("\nPlease select a life cycle from above or type 'new' to create a new life cycle. Enter -1 to EXIT");
    }

    public static LifeCycle createLifeCycle(Scanner input) {
        String name;
        String strain;
        //String nutrientLine;
        boolean isPhotoPeriod;
        String selection;
        System.out.println("\nNote: Please only enter a new life cycle when seedling's first set of true leaves have formed. Would you like to proceed?");
        System.out.println("Enter: Y/N?");
        selection = input.nextLine();
        if (!selection.equalsIgnoreCase("Y")) {
            return null;
        }
        System.out.println("\nWhat would you like to name your life cycle (Strain name, nickname, etc.)?");
        name = input.nextLine();
        System.out.print("\nSativa\nSativa Hybrid\nHybrid\nIndica Hybrid\nIndica\n");
        System.out.println("Type the strain from the list above: ");
        strain = input.nextLine();
        while (!strain.equalsIgnoreCase("sativa") && !strain.equalsIgnoreCase("sativa hybrid") && !strain.equalsIgnoreCase("hybrid") 
        && !strain.equalsIgnoreCase("indica hybrid") && !strain.equalsIgnoreCase("indica")) {
            System.out.println("Invalid entry, try again:");
            strain = input.nextLine();
        }
        System.out.println("\nIs this strain 'autoflowering' or 'photoperiod'?");
        selection = input.nextLine();
        while (!selection.equalsIgnoreCase("autoflowering") && !selection.equalsIgnoreCase("photoperiod")) {
            System.out.println("Invalid entry, try again:");
            selection = input.nextLine();
        }
        if (selection.equalsIgnoreCase("autoflowering")) {
            isPhotoPeriod = false;
        }
        else {
            isPhotoPeriod = true;
        }
        return new LifeCycle(name, strain, isPhotoPeriod);

    }

    public static void displayLifeCycle(LifeCycle entry) {
        System.out.println("\n" + entry.getName());
        System.out.println("Nutrient Line: " + entry.getNutrientLine());
        System.out.println(entry.getStrain());
        System.out.println(entry.getCycleType());
        System.out.println("Growth stage: " + entry.getPhase());
        System.out.println("Days since sprout: " + entry.getDaysElapsed());
    }

    public static void getNutrientInfo(LifeCycle entry, Scanner input) {
        double liters;
        final double base = 3.79;
        double micro = 0.0;
        double gro = 0.0;
        double bloom = 0.0;
        double calimagic = 0.0;
        double koolbloom = 0.0;
        int ppmLow;
        int ppmHigh;
        //epsom salt should display more accurate amounts...
        boolean epsom = false;
        int daysElapsed;
        System.out.print("Please enter the amount of liters you would like to fill: ");
        liters = Double.parseDouble(input.nextLine());
        //if stage < 3
        if (entry.getStage() < 3) {
            daysElapsed = entry.getDaysElapsed();
            if (daysElapsed < 7) {
                System.out.println("Seedling");
                micro = (2.5 / base) * liters;
                gro = (2.5 / base) * liters;
                bloom = (2.5 / base) * liters;
                ppmLow = 300;
                ppmHigh = 400;
            }
            else if (daysElapsed < 14) {
                System.out.println("Early Growth");
                micro = (3.8 / base) * liters;
                gro = (5.7 / base) * liters;
                bloom = (2.8 / base) * liters;
                calimagic = (2.0 / base) * liters;
                ppmLow = 650;
                ppmHigh = 800;
            }
            else if (daysElapsed < 21) {
                System.out.println("Early Growth");
                micro = (5.7 / base) * liters;
                gro = (7.6 / base) * liters;
                bloom = (3.8 / base) * liters;
                calimagic = (2.0 / base) * liters;
                ppmLow = 850;
                ppmHigh = 1050;
                epsom = true;
            }
            else {
                System.out.println("Late Growth");
                micro = (7.6 / base) * liters;
                gro = (8.5 / base) * liters;
                bloom = (4.7 / base) * liters;
                calimagic = (2.0 / base) * liters;
                ppmLow = 1050;
                ppmHigh = 1300;
                epsom = true;
            }
        }
        //else - this is when we compare current date to the date bloom began
        else {
            daysElapsed = entry.getDaysElapsedSinceBloom();
            if (daysElapsed < 7) {
                System.out.println("Early Bloom");
                micro = (5.7 / base) * liters;
                gro = (6.6 / base) * liters;
                bloom = (6.6 / base) * liters;
                calimagic = (2.0 / base) * liters;
                koolbloom = (1.0 / base) * liters;
                ppmLow = 950;
                ppmHigh = 1200;
                epsom = true;
            }
            else if (daysElapsed < 14) {
                System.out.println("Early Bloom");
                micro = (5.7 / base) * liters;
                gro = (6.6 / base) * liters;
                bloom = (6.6 / base) * liters;
                calimagic = (2.0 / base) * liters;
                koolbloom = (1.0 / base) * liters;
                ppmLow = 950;
                ppmHigh = 1200;
                epsom = true;
            }
            else if (daysElapsed < 21) {
                System.out.println("Mid Bloom");
                micro = (4.7 / base) * liters;
                gro = (3.8 / base) * liters;
                bloom = (8.5 / base) * liters;
                calimagic = (2.0 / base) * liters;
                koolbloom = (2.0 / base) * liters;
                ppmLow = 950;
                ppmHigh = 1200;
                epsom = true;
            }
            else if (daysElapsed < 28) {
                System.out.println("Mid Bloom");
                micro = (4.7 / base) * liters;
                gro = (3.8 / base) * liters;
                bloom = (8.5 / base) * liters;
                calimagic = (2.0 / base) * liters;
                koolbloom = (2.0 / base) * liters;
                ppmLow = 950;
                ppmHigh = 1200;
                epsom = true;
            }
            else if (daysElapsed < 35) {
                System.out.println("Mid Bloom");
                micro = (4.7 / base) * liters;
                gro = (3.8 / base) * liters;
                bloom = (8.5 / base) * liters;
                calimagic = (2.0 / base) * liters;
                koolbloom = (2.0 / base) * liters;
                ppmLow = 950;
                ppmHigh = 1200;
                epsom = true;
            }
            else if (daysElapsed < 42) {
                System.out.println("Late Bloom");
                micro = (3.8 / base) * liters;
                gro = (3.8 / base) * liters;
                bloom = (4.2 / base) * liters;
                calimagic = (2.0 / base) * liters;
                koolbloom = (1.0 / base) * liters;
                ppmLow = 650;
                ppmHigh = 850;
                epsom = true;
            }
            else if (daysElapsed < 49) {
                System.out.println("Late Bloom");
                micro = (3.8 / base) * liters;
                gro = (3.8 / base) * liters;
                bloom = (4.2 / base) * liters;
                calimagic = (2.0 / base) * liters;
                koolbloom = (1.0 / base) * liters;
                ppmLow = 650;
                ppmHigh = 850;
                epsom = true;
            }
            else {
                System.out.println("Ripen");
                System.out.println("Harvest Approaching!");
                micro = (2.8 / base) * liters;
                gro = (2.8 / base) * liters;
                bloom = (4.5 / base) * liters;
                ppmLow = 450;
                ppmHigh = 550;
            }
        }
        displayNutrients(liters, micro, gro, bloom, calimagic, koolbloom, epsom, ppmLow, ppmHigh);

    }

    public static void displayNutrients(double liters, double m, double g, double b, double cm, double kb, boolean e, int ppmL, int ppmH) {
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("\nFor " + liters + " liters, you will need:");
        System.out.println("Micro: " + df.format(m) + "(ml)");
        System.out.println("Gro: " + df.format(g) + "(ml)");
        System.out.println("Bloom: " + df.format(b) + "(ml)");
        if (cm > 0) {
            System.out.println("CaliMagic: " + df.format(cm) + "(ml)");
        }
        if (kb > 0) {
            System.out.println("KoolBloom: " + df.format(kb) + "(ml)");
        }
        if (e) {
            System.out.println("Pinch of epsom");
        }
        System.out.println("Target PPM: " + ppmL + " - " + ppmH);
    }

    public static void initiateBloom(LifeCycle entry, Scanner input) {
        String selection;
        System.out.println("");
        if (entry.getStage() >= 3) {
            System.out.println("Bloom already initiated.");
            return;
        }
        System.out.println("Have the first pistils emerged?");
        System.out.println("Enter: Y/N?");
        selection = input.nextLine();
        System.out.println("");
        if (selection.equalsIgnoreCase("Y")) {
            entry.bloom();
            System.out.println("Bloom phase initiated.");
        }
        else {
            System.out.println("Bloom phase not yet initiated.");
        }
    }
}
