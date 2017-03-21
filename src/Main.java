import RealMachine.RealMachine;
import VirtualMachine.VirtualMachine;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        RealMachine rm = new RealMachine();

        /**
         * viską apačioj perkelt į RM
         */
        System.out.println("If you want to load the program, type 'load' and the program's name");
        System.out.println("If you want to run the program, type 'run' and the program's name");
        System.out.println("If you want to exit, type 'exit'");
        System.out.println("If you want to run the program in debug mode, type 'debug' and the program's name");


        while (true) {

            Scanner reader = new Scanner(System.in);
            String input = reader.nextLine().toLowerCase().trim();


            if (input.equals("exit")) {
                break;
            } else {
                String[] tokens = input.split(" ");
                if (tokens.length != 2) {
                    throw new IllegalArgumentException();
                }
                String command = tokens[0];
                String program = tokens[1];

                if (command.equals("load")) {

                    /**
                     * Sukurti procesa RM.
                     * Blokavimas, laukiant įvedimo srauto resurso
                     * Rasti failą pavadinimu, kurį įvedė vartotojas
                     * Blokavimasis, laukiant buferio supervizorinėje atmintyje
                     * Eilutės kopijavimas į buferį
                     * „Užduotis supervizorinėje atmintyje“ resurso sukūrimas
                     */

                } else if (command.equals("run")) {

                    /**
                     * Sukurti procesa RM.
                     * Blokavimas, laukiant resurso „Užduotis supervizorineje atmintyje“
                     * Užduotyje aptikta klaidų?
                     * -> Taip ->
                     * Kuriamas resursas „Eilutė supervizorinėje atmintyje“ su klaidos pranešimu
                     * Atlaisvinti „Eilutė supervizorinėje atmintyje“ resursą su klaidos pranešimu
                     * -> Ne ->
                     * Kurimas resursas „Užduoties parametrai supervizorineje atmintyje“
                     * Kuriamas resuras „Užduoties programa supervizorineje atmintyje“
                     * Kuriamas resuras „Užduoties duomenys supervizorineje atmintyje“
                     * Atlaisvinti „Eilutė supervizorinėje atmintyje“ resursą su klaidos pranešimu
                     */

                } else if (command.equals("debug")) {
                    /**
                     * Sukurti procesa VM.
                     * Blokavimasis, laukiant resurso „Užduoties vykdymo parametrai“
                     * Blokavimasis, laukiant resurso „Užduoties promrama supervizorineje atmintyje“
                     * Blokavimasis, laukiant resurso „Užduoties duomenys supervizorineje atmintyje“
                     * Blokavimasis, laukiant „išorinės atmintis“ resurso
                     * Blokavimasis, laukiant „3-ojo kanalo“ resurso
                     * Pranešimo procesui „Chan3_Device“ sukūrimas
                     * Blokavimasis laukiant „Chan3_Device“ darbo pabaigos pranešimo
                     * Resurso „Užduotis diske“ sukūrimas
                     * „Supervizorinės atminties“ resurso atlaisvinimas
                     */

                } else
                    break;
            }

        }
    }


}
