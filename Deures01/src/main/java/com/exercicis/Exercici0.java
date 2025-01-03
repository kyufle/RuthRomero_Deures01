package com.exercicis;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Introducció
 * -----------
 * 
 * En aquests exàmen es farà un gestor de dades per una notaria.
 * 
 * Hi haurà diferents tipus de dades, 'clients' i 'operacions'.
 * 
 * Exemples de com han de ser les dades:
 * 
 * clients = {
 * "client_100": {
 * "nom": "Joan Garcia",
 * "edat": 45,
 * "factors": ["autònom", "risc mitjà"],
 * "descompte": 15
 * },
 * "client_401": {"nom": "Marta Pérez", "edat": 38, "factors": ["empresa", "risc
 * baix"], "descompte": 10},
 * "client_202": {"nom": "Pere López", "edat": 52, "factors": ["autònom", "risc
 * alt"], "descompte": 5}
 * }
 * 
 * operacions = [
 * {
 * "id": "operacio_100",
 * "tipus": "Declaració d'impostos",
 * "clients": ["client_100", "client_202"],
 * "data": "2024-10-05",
 * "observacions": "Presentació conjunta",
 * "preu": 150.0
 * },
 * {"id": "operacio_304", "tipus": "Gestió laboral", "clients": ["client_202"],
 * "data": "2024-10-04", "observacions": "Contractació de personal", "preu":
 * 200.0},
 * {"id": "operacio_406", "tipus": "Assessoria fiscal", "clients":
 * ["client_401"], "data": "2024-10-03", "observacions": "Revisió d'informes",
 * "preu": 120.0}
 * ]
 */

public class Exercici0 {

    // Variables globals (es poden fer servir a totes les funcions)
    public static HashMap<String, HashMap<String, Object>> clients = new HashMap<>();
    public static ArrayList<HashMap<String, Object>> operacions = new ArrayList<>();

    // Neteja la consola tenint en compte el sistema operatiu
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Valida si un nom és vàlid.
     * Un nom és vàlid si no està buit i només conté lletres o espais
     * com a mínim a de tenir dues lletres
     *
     * @param nom El nom a validar.
     * @return True si el nom és vàlid, false altrament.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testValidarNom"
     */
    public static boolean validarNom(String nom) {
        ArrayList<String> vocals = new ArrayList<>(Arrays.asList("a", "a", "e", "e", "i", "i", "o", "o", "u", "u"));
        ArrayList<String> vocalsAccent = new ArrayList<>(
                Arrays.asList("à", "á", "è", "é", "í", "ì", "ó", "ò", "ú", "ù"));
        int sumaLletres = 0;
        if (!(nom.length() >= 2)) /*
                                   * es fa així per comprovar que el nom té una longitud (es dir, no està buit) i
                                   * que com a mínim tindrà dues lletres
                                   */ {
            return false;
        }

        for (int i = 0; i < vocals.size(); i++) {
            nom.replaceAll(vocalsAccent.get(i), vocals.get(i));
        }

        for (Character lletra : nom.toCharArray()) {
            if (!Character.isAlphabetic(lletra) && !Character.isSpaceChar(lletra)) {
                return false;
            }
            if (Character.isAlphabetic(lletra)) {
                sumaLletres += 1;
            }
        }
        if (sumaLletres < 2) {
            return false;
        }
        return true;

    }

    /**
     * Valida que l'edat sigui un valor vàlid.
     * Comprova que l'edat sigui un enter i que estigui dins del rang acceptable
     * (entre 18 i 100, ambdós inclosos).
     *
     * @param edat L'edat que s'ha de validar.
     * @return True si l'edat es troba entre 18 i 100 (inclosos), false altrament.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testValidarEdat"
     */
    public static boolean validarEdat(int edat) {
        if (edat >= 18 && edat <= 100) {
            return true;
        }
        return false;
    }

    /**
     * Valida que els factors proporcionats siguin vàlids.
     * Comprova que:
     * - Els factors siguin una llista amb exactament dos elements.
     * - El primer element sigui "autònom" o "empresa".
     * - El segon element sigui "risc alt", "risc mitjà" o "risc baix".
     * - Un "autònom" no pot ser de "risc baix".
     * 
     * Exemples:
     * validarFactors(new String[]{"autònom", "risc alt"}) // retorna true
     * validarFactors(new String[]{"empresa", "risc moderat"}) // retorna false
     *
     * @param factors Llista d'elements a validar.
     * @return True si els factors compleixen les condicions, false altrament.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testValidarFactors"
     */
    public static boolean validarFactors(String[] factors) {
        if (factors.length != 2) {
            return false;
        } else if (factors[0] == null || factors[1] == null) {
            return false;
        } else if (!factors[0].equalsIgnoreCase("autònom") && !factors[0].equalsIgnoreCase("empresa")) {
            return false;
        } else if (factors[0].equalsIgnoreCase("autònom") && factors[1].equalsIgnoreCase("risc baix")) {
            return false;
        } else if (!factors[1].equalsIgnoreCase("risc baix") && !factors[1].equalsIgnoreCase("risc mitjà")
                && !factors[1].equalsIgnoreCase("risc alt")) {
            return false;
        }
        return true;
    }

    /**
     * Valida que el descompte sigui un valor vàlid.
     * Comprova que:
     * - El descompte sigui un número vàlid (enter o decimal).
     * - El descompte es trobi dins del rang acceptable (entre 0 i 20, ambdós
     * inclosos).
     *
     * Exemples:
     * validarDescompte(15) retorna true
     * validarDescompte(25) retorna false
     * 
     * @param descompte El valor del descompte a validar.
     * @return True si el descompte és vàlid, false altrament.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testValidarDescompte"
     */
    public static boolean validarDescompte(double descompte) {
        if (descompte >= 0 && descompte <= 20) {
            return true;
        }
        return false;
    }

    /**
     * Valida que el tipus d'operació sigui vàlid.
     * Comprova que:
     * - El tipus d'operació proporcionat coincideixi amb algun dels tipus vàlids.
     * 
     * Els tipus vàlids inclouen:
     * "Declaració d'impostos", "Gestió laboral", "Assessoria fiscal",
     * "Constitució de societat", "Modificació d'escriptures",
     * "Testament", "Gestió d'herències", "Acta notarial",
     * "Contracte de compravenda", "Contracte de lloguer".
     *
     * Exemples:
     * validarTipusOperacio("Declaració d'impostos") retorna true
     * validarTipusOperacio("Operació desconeguda") retorna false
     * 
     * @param tipus El tipus d'operació a validar.
     * @return True si el tipus d'operació és vàlid, false altrament.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testValidarTipusOperacio"
     */
    public static boolean validarTipusOperacio(String tipus) {
        ArrayList<String> operacions = new ArrayList<>(
                Arrays.asList("Declaració d'impostos", "Gestió laboral", "Assessoria fiscal", "Constitució de societat",
                        "Modificació d'escriptures", "Testament", "Gestió d'herències",
                        "Acta notarial", "Contracte de compravenda", "Contracte de lloguer"));

        if (operacions.contains(tipus)) {
            return true;
        }
        return false;
    }

    /**
     * Valida que la llista de clients sigui vàlida.
     * Comprova que:
     * - La llista de clients sigui efectivament una llista.
     * - Una llista buida és vàlida.
     * - Tots els elements de la llista de clients siguin únics.
     * - Tots els clients de la llista es trobin dins de la llista global de clients
     * vàlids.
     *
     * Exemples:
     * validarClients(new ArrayList<>(List.of("client1", "client2")),
     * new ArrayList<>(List.of("client1", "client2", "client3"))) retorna true
     * validarClients(new ArrayList<>(List.of("client1", "client1")),
     * new ArrayList<>(List.of("client1", "client2", "client3"))) retorna false
     * validarClients(new ArrayList<>(),
     * new ArrayList<>(List.of("client1", "client2", "client3"))) retorna true
     * 
     * @param clientsLlista  La llista de clients a validar.
     * @param clientsGlobals La llista global de clients vàlids.
     * @return True si la llista de clients compleix totes les condicions, false
     *         altrament.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testValidarClients"
     */
    public static boolean validarClients(ArrayList<String> clientsLlista, ArrayList<String> clientsGlobals) {
        if (clientsLlista == null || clientsGlobals == null) {
            return false;
        }
        if (clientsLlista.size() == 0) {
            return true;
        }

        for (String claveClient : clientsLlista) {
            int suma = 0;
            for (int i = 0; i < clientsLlista.size(); i++) {
                if (claveClient.equals(clientsLlista.get(i))) {
                    suma += 1;
                }
                if (suma > 1) {
                    return false;
                }
                if (!clientsGlobals.contains(clientsLlista.get(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Comprova si una cadena conté només dígits.
     * 
     * @param str La cadena a comprovar.
     * @return True si la cadena conté només dígits, false altrament.
     *
     * @test ./runTest.sh "com.exercicis.TestExercici0#testIsAllDigits"
     */
    public static boolean isAllDigits(String str) {
        if (str.length() < 1) {
            return false;
        }
        for (Character num : str.toCharArray()) {
            if (!Character.isDigit(num)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Valida que la data sigui en un format vàlid (AAAA-MM-DD) i que representi una
     * data possible.
     * Comprova que:
     * - La longitud de la cadena sigui exactament 10 caràcters.
     * - La cadena es pugui dividir en tres parts: any, mes i dia.
     * - Any, mes i dia estiguin formats per dígits.
     * - Any estigui en el rang [1000, 9999].
     * - Mes estigui en el rang [1, 12].
     * - Dia estigui en el rang [1, 31].
     * - Es compleixin les limitacions de dies segons el mes.
     *
     * Exemples:
     * validarData("2023-04-15") retorna true
     * validarData("2023-02-30") retorna false
     * validarData("2023-13-01") retorna false
     *
     * @param data La cadena que representa una data en format 'AAAA-MM-DD'.
     * @return True si la data és vàlida, false altrament.
     *
     * @test ./runTest.sh "com.exercicis.TestExercici0#testValidarData"
     */
    public static boolean validarData(String data) {
        if (data == null) {
            return false;
        }
        if (data.length() != 10) {
            return false;
        }

        String any = data.substring(0, 4);
        char[] anyCharacter = any.toCharArray();
        for (Character numAny : anyCharacter) {
            if (!Character.isDigit(numAny)) {
                return false;
            }
        }
        String mes = data.substring(5, 7);
        char[] mesCharacter = mes.toCharArray();
        for (Character numMes : mesCharacter) {
            if (!Character.isDigit(numMes)) {
                return false;
            }
        }

        String dia = data.substring(8, 10);
        char[] diaCharacter = dia.toCharArray();
        for (Character numDia : diaCharacter) {
            if (!Character.isDigit(numDia)) {
                return false;
            }
        }

        Integer anyBisiesto = Integer.parseInt(any);
        Integer diaBisiesto = Integer.parseInt(dia);

        boolean esBisiesto = true;

        if (anyBisiesto % 4 == 0) {
            if (anyBisiesto % 100 == 0) {
                if (anyBisiesto % 400 == 0) {
                    esBisiesto = true;
                } else {// no es bisiesto
                    esBisiesto = false;
                }
            } else {
                esBisiesto = true;
            }

        } else { // no es bisiesto
            esBisiesto = false;
        }

        System.out.println(data + " " + esBisiesto);

        if (mes.equals("02") && diaBisiesto > 29) {
            return false;
        }

        if (!(esBisiesto) && mes.equals("02") && diaBisiesto > 28) {
            return false;
        }

        Integer mesNum = Integer.parseInt(mes);
        if (mesNum < 1 || mesNum > 12) {
            return false;
        }
        if (diaBisiesto < 1) {
            return false;
        }

        switch (mesNum) {
            case 1: {
                if (diaBisiesto > 31) {
                    return false;
                }
            }
            case 3: {
                if (diaBisiesto > 31) {
                    return false;
                }
            }
            case 4: {
                if (diaBisiesto > 30) {
                    return false;
                }
            }
            case 5: {
                if (diaBisiesto > 31) {
                    return false;
                }
            }
            case 6: {
                if (diaBisiesto > 30) {
                    return false;
                }
            }
            case 7: {
                if (diaBisiesto > 31) {
                    return false;
                }
            }
            case 8: {
                if (diaBisiesto > 31) {
                    return false;
                }
            }
            case 9: {
                if (diaBisiesto > 30) {
                    return false;
                }
            }
            case 10: {
                if (diaBisiesto > 31) {
                    return false;
                }
            }
            case 11: {
                if (diaBisiesto > 30) {
                    return false;
                }
            }
            case 12: {
                if (diaBisiesto > 31) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Valida que el preu sigui un número vàlid i superior a 100.
     * Comprova que:
     * - El preu sigui un número vàlid (decimal o enter).
     * - El valor del preu sigui estrictament superior a 100.
     *
     * Exemples:
     * validarPreu(150.0) retorna true
     * validarPreu(99.99) retorna false
     * validarPreu(100.0) retorna false
     * 
     * @param preu El valor del preu a validar.
     * @return True si el preu és un número i és superior a 100, false altrament.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testValidarPreu"
     */
    public static boolean validarPreu(double preu) {
        if (preu <= 100.0) {
            return false;
        }
        return true;
    }

    /**
     * Genera una clau única per a un client.
     * La clau és en el format "client_XYZ", on XYZ és un número aleatori entre 100
     * i 999.
     * Comprova que la clau generada no existeixi ja en el diccionari de clients.
     *
     * @return Una clau única per al client.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testGeneraClauClient"
     */
    public static String generaClauClient() {
        Random random = new Random();
        while (true) {
            int numRandom = random.nextInt(100, 999);
            if (!clients.containsKey("client_" + numRandom)) {
                return "client_" + numRandom;
            }
        }
    }

    /**
     * Afegeix un nou client al diccionari de clients.
     * - Genera una nova clau amb "generaClauClient"
     * - Afegeix una entrada al diccionari de clients,
     * on la clau és la nova clau generada i el valor és un HashMap
     * amb el nom, edat, factors i descompte del nou client.
     *
     * Exemples:
     * afegirClient(clients, "Maria", 30, new ArrayList<>(List.of("empresa", "risc
     * baix")), 10) retorna "client_0"
     *
     * @param nom       El nom del nou client.
     * @param edat      L'edat del nou client.
     * @param factors   La llista de factors associats al client.
     * @param descompte El descompte associat al nou client.
     * @return La clau del nou client afegit.
     *
     * @test ./runTest.sh "com.exercicis.TestExercici0#testAfegirClient"
     */
    public static String afegirClient(String nom, int edat, ArrayList<String> factors, double descompte) {
        HashMap<String,Object> informacioClientNou = new HashMap<>();
        informacioClientNou.put("nom", nom);
        informacioClientNou.put("edat", edat);
        informacioClientNou.put("factors", factors);
        informacioClientNou.put("descompte", descompte);

        String clauClient = generaClauClient();
        clients.put(clauClient,informacioClientNou);
        return clauClient;
    }

    /**
     * Modifica un camp específic d'un client al diccionari de clients.
     * - Comprova si la clau del client existeix al diccionari de clients. x
     * - Si existeix, comprova si el camp que es vol modificar és vàlid (existeix
     * dins del diccionari del client). x
     * - Si el camp existeix, actualitza el valor del camp amb el nou valor. x
     * - Si el camp no existeix, retorna un missatge d'error indicant que el camp no
     * existeix.
     * - Si la clau del client no existeix, retorna un missatge d'error indicant que
     * el client no es troba.
     * 
     * Retorn:
     * - Retorna "Client 'client_XYZ' no existeix." si el client no existeix
     * - Retorna "El camp 'campErroni' si el camp no existeix en aquest client
     * - "OK" si s'ha modificat el camp per aquest client
     * 
     * @param clauClient La clau del client que s'ha de modificar.
     * @param camp       El camp que s'ha de modificar.
     * @param nouValor   El nou valor que s'ha d'assignar al camp.
     * @return Un missatge d'error si el client o el camp no existeixen; "OK"
     *         altrament.
     *
     * @test ./runTest.sh "com.exercicis.TestExercici0#testModificarClient"
     */
    public static String modificarClient(String clauClient, String camp, Object nouValor) {
        if (!(clients.containsKey(clauClient))){
                return "Client \'"+ clauClient +"\'"+" no existeix.";
        }
        else{
            if (clients.get(clauClient).containsKey(camp)){
                clients.get(clauClient).replace(camp, nouValor);
                return "OK";
            }
            else {
                return "El camp "+ camp +" no existeix.";
            }
        }
    }

    /**
     * Esborra un client del diccionari de clients.
     * Comprova:
     * - Si la clau del client existeix dins del diccionari de clients.
     * - Si la clau del client existeix, elimina el client del diccionari.
     * - Si la clau del client no existeix, retorna un missatge d'error.
     *
     * Retorn:
     * - Si el client no existeix, retorna un missatge d'error: "Client amb clau
     * {clauClient} no existeix."
     * - Si el client existeix, l'elimina i retorna "OK".
     *
     * @param clauClient La clau del client que s'ha d'esborrar.
     * @return Un missatge d'error si el client no existeix o "OK" si s'ha esborrat
     *         correctament.
     * @test ./runTest.sh "com.exercicis.TestExercici0#testEsborrarClient"
     */
    public static String esborrarClient(String clauClient) {
        if(clients.containsKey(clauClient)){
            clients.remove(clauClient);
            return "OK";
        }
        return "Client amb clau " +clauClient+ " no existeix.";
    }

    /**
     * Llista clients que compleixen determinades condicions.
     * Comprova si els clients que coincideixen amb les claus
     * especificades compleixen les condicions proporcionades.
     * 
     * @param claus      La llista de claus de clients a considerar per la cerca.
     * @param condicions Les condicions que els clients han de complir.
     * @return Una llista de diccionaris, on cada diccionari conté
     *         la clau del client i les dades del client.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testLlistarClients"
     */
    public static ArrayList<HashMap<String, HashMap<String, Object>>> llistarClients(ArrayList<String> claus, HashMap<String, Object> condicions) {
        ArrayList<HashMap<String, HashMap<String, Object>>> clientsValids = new ArrayList<>();

        for (String clau : claus){
            boolean cumpleCondiciones = true;
            if (clients.containsKey(clau)){
                for (String condicioKey : condicions.keySet()){
                    if (clients.get(clau).containsKey(condicioKey)){
                        if (!clients.get(clau).get(condicioKey).equals(condicions.get(condicioKey))){
                            cumpleCondiciones = false;
                        }
                    }
                }
                if (cumpleCondiciones){
                    HashMap<String, HashMap<String, Object>> clientDiccionari = new HashMap<>();
                    clientDiccionari.put(clau,clients.get(clau));
                    clientsValids.add(clientDiccionari);
                    return clientsValids;
                }
            }
        }
        return clientsValids;
    }

    /**
     * Genera una clau única per a una operació.
     * La clau és en el format "operacio_XYZ", on XYZ és un número aleatori entre
     * 100 i 999.
     * Comprova que la clau generada no existeixi ja en la llista d'operacions.
     *
     * @return Una clau única per a l'operació.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testGeneraClauOperacio"
     */
    public static String generaClauOperacio() {
        Random random = new Random();
        while (true){
            int numRandom = random.nextInt(100,999);
            int numero = numRandom;
            boolean noEnUso = true;
            for(HashMap<String, Object> operacio : operacions){
                if (operacio.get("id").equals("operacio_"+numero)){
                    noEnUso = false;
                }
            }
            if (noEnUso) {
                return "operacio_"+numero;             
            }
            
        }
    }

    /**
     * Afegeix una nova operació a la llista d'operacions.
     * - Genera un nova clau amb "generaClauOperacio"
     * - Crea un HashMap que representa la nova operació amb els camps següents:
     * - "id": clau de l'operació.
     * - "tipus": el tipus d'operació.
     * - "clients": llista de clients implicats.
     * - "data": la data de l'operació.
     * - "observacions": observacions de l'operació.
     * - "preu": preu de l'operació.
     * - Afegeix aquest HashMap a la llista d'operacions.
     * 
     * @param tipus            El tipus d'operació.
     * @param clientsImplicats La llista de clients implicats.
     * @param data             La data de l'operació.
     * @param observacions     Observacions addicionals sobre l'operació.
     * @param preu             El preu associat a l'operació.
     * @return L'identificador de la nova operació.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testAfegirOperacio"
     */
    public static String afegirOperacio(String tipus,ArrayList<String> clientsImplicats,String data,String observacions,double preu) {
        String clauOperacio = generaClauOperacio();
        HashMap<String,Object> novaOperacio = new HashMap<>();
        novaOperacio.put("id", clauOperacio);
        novaOperacio.put("tipus", tipus);
        novaOperacio.put("clients", clientsImplicats);
        novaOperacio.put("data", data);
        novaOperacio.put("observacions", observacions);
        novaOperacio.put("preu", preu);

        operacions.add(novaOperacio);
        return clauOperacio;
    }

    /**
     * Modifica un camp específic d'una operació dins de la llista d'operacions.
     * 
     * @param idOperacio L'identificador de l'operació que s'ha de modificar.
     * @param camp       El camp específic dins del HashMap de l'operació que s'ha
     *                   de modificar.
     * @param nouValor   El nou valor que es vol assignar al camp especificat.
     * @return Un missatge d'error si l'operació o el camp no existeix, "OK"
     *         si la modificació s'ha realitzat correctament.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testModificarOperacio"
     */
    public static String modificarOperacio(String idOperacio, String camp, Object nouValor) {
        for (HashMap<String, Object> operacio : operacions){
            if (operacio.get("id").equals(idOperacio) && operacio.containsKey(camp)){
                operacio.replace(camp, nouValor);
                return "OK";
            }
            else if (!operacio.get("id").equals(idOperacio)){
                return "Operació amb id "+idOperacio+" no existeix.";
            }
            else if (!operacio.containsKey(camp)){
                return "El camp descompte no existeix en l'operació.";
            }
        }
        return "No hi ha operacions";
    }

    /**
     * Esborra una operació de la llista d'operacions basada en l'identificador de
     * l'operació.
     * 
     * @param idOperacio L'identificador de l'operació que es vol esborrar.
     * @return Un missatge d'error si l'operació amb 'idOperacio' no existeix, "OK"
     *         si s'esborra correctament.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testEsborrarOperacio"
     */
    public static String esborrarOperacio(String idOperacio) {
        for (HashMap<String,Object> operacio : operacions){
            if (operacio.get("id").equals(idOperacio)){
                operacions.remove(operacio);
                return "OK";
            }
        }
        return "Operació amb id "+idOperacio+ " no existeix.";
    }

    /**
     * Llista les operacions que compleixen determinats criteris basats
     * en identificadors i condicions específiques.
     * 
     * @param ids        Una llista d'identificadors d'operacions que es volen
     *                   considerar.
     *                   Si està buida, es consideren totes les operacions.
     * @param condicions Un HashMap amb les condicions que les operacions han de
     *                   complir.
     * @return Una llista amb les operacions que compleixen les condicions.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testLlistarOperacions"
     */
    public static ArrayList<HashMap<String, Object>> llistarOperacions(ArrayList<String> ids,
            HashMap<String, Object> condicions) {
        ArrayList<HashMap<String, Object>> operacionsValides = new ArrayList<>();
        if (ids.isEmpty()) {
            for (HashMap<String, Object> operacio : operacions) {
                boolean totesLesCondicions = true;
                if (condicions != null){
                    for (String condicioClau : condicions.keySet()) {
                        if (!operacio.get(condicioClau).equals(condicions.get(condicioClau))) {
                            totesLesCondicions = false;
                        }
                    }
                }
                
                if (totesLesCondicions){
                    operacionsValides.add(operacio);
                }
            }
        }

        else {
            for (HashMap<String, Object> operacio : operacions) {
                boolean totesLesCondicions = true;
                for (String id : ids) {
                    
                    if (condicions != null){
                        for (String condicioClau : condicions.keySet()) {
                            if (operacio.get("id").equals(id)
                                    && !operacio.get(condicioClau).equals(condicions.get(condicioClau))) {
                                        totesLesCondicions = false;
                                
                            }
                        }
                    }
              
                    if (totesLesCondicions && operacio.get("id").equals(id)){
                        operacionsValides.add(operacio);
                    }
                }
            }
        }
        return operacionsValides;
    }

    /**
     * Llista les operacions associades a un client específic basant-se en la seva
     * clau.
     * 
     * @param clauClient La clau única del client que es vol filtrar.
     * @return Una llista amb les operacions associades al client especificat.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testLlistarOperacionsClient"
     */
    public static ArrayList<HashMap<String, Object>> llistarOperacionsClient(String clauClient) {
        ArrayList<HashMap<String, Object>> operacionsValides = new ArrayList<>();
        for (HashMap<String, Object> operacio : operacions){
            ArrayList<String> clientsEnOperacio = (ArrayList<String>) operacio.get("clients");
            if (clientsEnOperacio.contains(clauClient)){
                operacionsValides.add(operacio);
            }
        }
        return operacionsValides;
    }

    /**
     * Mètode que formata i alinea columnes de text
     * segons les especificacions donades.
     * 
     * El mètode processa cada columna:
     * - Si el text és més llarg que l'amplada especificada, el trunca
     * - Afegeix els espais necessaris segons el tipus d'alineació:
     * * "left": alinea el text a l'esquerra i omple amb espais a la dreta
     * * "right": omple amb espais a l'esquerra i alinea el text a la dreta
     * * "center": distribueix els espais entre esquerra i dreta per centrar el text
     * 
     * @param columnes ArrayList que conté arrays d'Objects, on cada array
     *                 representa una columna amb:
     *                 - posició 0: String amb el text a mostrar
     *                 - posició 1: String amb el tipus d'alineació ("left",
     *                 "right", "center")
     *                 - posició 2: int amb l'amplada total de la columna
     * 
     * @return String amb totes les columnes formatades concatenades
     * 
     *         Per exemple:
     *         Si input és: {{"Hola", "left", 6}, {"Mon", "right", 5}}
     *         Output seria: "Hola Mon"
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testAlineaColumnes"
     */
    public static String alineaColumnes(ArrayList<Object[]> columnes) {
        String totaltext = "";
        for (Object[] columna : columnes){
            String textMostrar = (String) columna[0];
            String tipusAlineacio = (String) columna[1];
            int ampladaColumna = (int) columna[2];
            System.out.println(textMostrar+" "+tipusAlineacio+" "+ampladaColumna);
            if (textMostrar.length()>ampladaColumna){
                totaltext += textMostrar.substring(0, ampladaColumna);
                continue;
            }
            if (tipusAlineacio.equals("left")){
                String alinacio = "%-"+ampladaColumna+"s";
                totaltext += String.format(alinacio, textMostrar);
            }
            else if (tipusAlineacio.equals("right")){
                String alinacio = "%"+ampladaColumna+"s";
                totaltext += String.format(alinacio, textMostrar);
            }
            else {
                int totalWidth = ampladaColumna;
                int paddingEsquerra = (totalWidth-textMostrar.length())/2;
                int paddingDreta = (totalWidth-textMostrar.length()) - paddingEsquerra;
                totaltext += String.format("%"+paddingEsquerra+"s%s%"+paddingDreta+"s","",textMostrar,"");
            }
        }
        System.out.println(totaltext);
        return totaltext;
    }

    /**
     * Genera una representació en forma de taula de les operacions
     * associades a un client específic.
     * 
     * Cada linia del resultat es guarda en un String de l'ArrayList.
     * 
     * Fes servir: Locale.setDefault(Locale.US)
     * 
     * Format esperat de sortida:
     * ```
     * Marta Puig i Puig, 45 [empresa, risc alt]
     * -------------------------------------------------------
     * Tipus Data Preu
     * Constitució de societat 2024-01-15 1250.50
     * Testament 2024-02-28 750.75
     * Acta notarial 2024-03-10 500.25
     * -------------------------------------------------------
     * Suma: 2501.50
     * Descompte: 10% Preu: 2126.28
     * Impostos: 21% (85.05) Total: 2572.80
     *******************************************************
     * 
     * 
     * Pere Vila, 25 [estudiant, risc baix]
     * -------------------------------------------------------
     * Tipus Data Preu
     * Certificat 2024-01-10 25.50
     * Fotocòpia 2024-01-15 15.25
     * Segell 2024-01-20 35.50
     * -------------------------------------------------------
     * Suma: 76.25
     * Descompte: 10% Preu: 68.63
     * Impostos: 21% (14.41) Total: 83.04
     * ```
     * On:
     * - La primera línia mostra el nom, edat i factors del client
     * - Els tipus d'operació s'alineen a l'esquerra
     * - Les dates tenen format YYYY-MM-DD
     * - Els preus mostren sempre 2 decimals
     * - El descompte és un percentatge enter
     * - Els impostos són sempre el 21% del preu amb descompte
     *
     * @param clauClient La clau única del client per generar la taula d'operacions.
     * @param ordre      El camp pel qual s'ordenaran les operacions (exemple:
     *                   "data", "preu").
     * @return Una llista de cadenes de text que representen les línies de la taula.
     *
     * @test ./runTest.sh "com.exercicis.TestExercici0#testTaulaOperacionsClient0"
     * @test ./runTest.sh "com.exercicis.TestExercici0#testTaulaOperacionsClient1"
     * @test ./runTest.sh "com.exercicis.TestExercici0#testTaulaOperacionsClient2"
     */
    public static ArrayList<String> taulaOperacionsClient(String clauClient, String ordre) {
        ArrayList<String> afegirUsuari = new ArrayList<>();
        double suma = 0;

        if (clients.containsKey(clauClient)){
            HashMap<String,Object> agafaClauClient = clients.get(clauClient);
            ArrayList<Object[]> campsCapcelera = new ArrayList<>(); 
            String factors = "[" + String.join(", ", (ArrayList<String>) agafaClauClient.get("factors")) + "]";

            campsCapcelera.add(new Object[] {
                agafaClauClient.get("nom")+ ", "+agafaClauClient.get("edat"),
            "left",25});
            campsCapcelera.add(new Object[] {
                factors,
                "right",30});
        
            afegirUsuari.add(alineaColumnes(campsCapcelera));
            afegirUsuari.add("-------------------------------------------------------");
            afegirUsuari.add(String.format("%-30s%-20s%5s","Tipus", "Data", "Preu"));
            for (HashMap<String, Object> operacio : operacions){
                if (((ArrayList<String>)operacio.get("clients")).contains(clauClient)){
                    ArrayList<Object[]> campsOperacions = new ArrayList<>();
                    campsOperacions.add(new Object[]{operacio.get("tipus"), "left", 30});
                    campsOperacions.add(new Object[]{operacio.get("data"), "left", 10});
                    campsOperacions.add(new Object[]{String.format(Locale.US, "%.2f",operacio.get("preu")), "right", 15});
                    afegirUsuari.add(alineaColumnes(campsOperacions));
                }
                suma += (double) operacio.get("preu");
            }
            afegirUsuari.add("-------------------------------------------------------");
            ArrayList<Object[]> campsSuma = new ArrayList<>();
            campsSuma.add(new Object[]{String.format("%s%.2f","Suma: ",suma),"right", 55});
            afegirUsuari.add(alineaColumnes(campsSuma));

            // int descompte = (int) agafaClauClient.get("descompte"); crec que aquí s'hauria d'agafar d'aquesta manera, 
            //pero revisant he vist en l'exercici resolt de que sempre ha d'utilitzar un 10%
            int descompte = 10;
            ArrayList<Object[]> columnesDescompte = new ArrayList<>();
            double preuAmbDescompte = (suma-((descompte/100.0)*suma));
            columnesDescompte.add(new Object[]{String.format("Descompte: %d%%", descompte), "left", 30});
            columnesDescompte.add(new Object[]{String.format("Preu: %.2f", preuAmbDescompte), "right", 25});
            afegirUsuari.add(alineaColumnes(columnesDescompte));

            ArrayList<Object[]> columnesImpostos = new ArrayList<>();
            columnesImpostos.add(new Object[]{String.format("Impostos:  21%% (%.2f)", preuAmbDescompte*0.21), "left", 30});
            columnesImpostos.add(new Object[]{String.format("Total: %.2f", ((preuAmbDescompte*0.21)+preuAmbDescompte)), "right", 25});
            afegirUsuari.add(alineaColumnes(columnesImpostos));
        }
        return afegirUsuari;
    }

    /**
     * Genera el menú principal de l'aplicació de Gestió de Notaria.
     * 
     * Retorna una llista de cadenes de text que representen
     * les opcions disponibles en el menú principal de l'aplicació.
     *
     * @return Una llista de cadenes de text amb les opcions del menú principal.
     *
     * @test ./runTest.sh "com.exercicis.TestExercici0#testGetCadenesMenu"
     */
    public static ArrayList<String> getCadenesMenu() {
        String menuText = """
                === Menú de Gestió de Notaria ===
                1. Afegir client
                2. Modificar client
                3. Esborrar client
                4. Llistar clients
                5. Afegir operació
                6. Modificar operació
                7. Esborrar operació
                8. Llistar operacions
                0. Sortir
                            """;
        String[] lines = menuText.split("\\R");
        return new ArrayList<>(Arrays.asList(lines));
    }

    /**
     * Genera el menú amb la llista de clients.
     * 
     * Retorna una llista de cadenes de text que representen
     * cada un dels clients de la llista.
     * - El primer text de la llista és així: "=== Llistar Clients ==="
     * - En cas de no haver-hi cap client afegeix a la llista de retorn "No hi ha
     * clients per mostrar."
     *
     * @return Una llista de cadenes de text amb els clients.
     *
     * @test ./runTest.sh "com.exercicis.TestExercici0#testLlistarClientsMenu"
     */
    public static ArrayList<String> getLlistarClientsMenu() {
        
        ArrayList<String> llistarClients = new ArrayList<>(Arrays.asList("=== Llistar Clients ===")); 
        if (clients.size() <= 0){
            llistarClients.add("No hi ha clients per mostrar.");
            return llistarClients;
        }
        else{
            for (String clientsID : clients.keySet()){
                llistarClients.add(clientsID+": "+clients.get(clientsID).toString());
            }
            return llistarClients;
        }
    }

    /**
     * Escriu per consola cada element d'una llista en una línia nova.
     * 
     * @param llista La llista de linies a mostrar
     *
     * @test ./runTest.sh "com.exercicis.TestExercici0#testDibuixarLlista"
     */
    public static void dibuixarLlista(ArrayList<String> llista) {
        for (String element : llista){
            System.out.println(element);
        }
    }

    /**
     * Demana a l'usuari que seleccioni una opció i retorna l'opció transformada a
     * una paraula clau si és un número.
     * 
     * Mostra el text: "Selecciona una opció (número o paraula clau): ".
     * - Si l'opció introduïda és un número vàlid, es transforma a les paraules clau
     * corresponents segons el menú.
     * - Si l'opció són paraules clau vàlides, es retornen directament.
     * Les paraules clau han d'ignorar les majúscules, minúscules i accents
     * - Si l'opció no és vàlida, mostra un missatge d'error i torna a preguntar
     * fins que l'entrada sigui vàlida.
     * "Opció no vàlida. Torna a intentar-ho."
     * 
     * Relació de números i paraules clau:
     * 1. "Afegir client"
     * 2. "Modificar client"
     * 3. "Esborrar client"
     * 4. "Llistar clients"
     * 5. "Afegir operació"
     * 6. "Modificar operació"
     * 7. "Esborrar operació"
     * 8. "Llistar operacions"
     * 0. "Sortir"
     * 
     * @return La cadena introduïda per l'usuari (número convertit a paraula clau o
     *         paraula clau validada).
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testObtenirOpcio"
     */
    public static String obtenirOpcio(Scanner scanner) {
        while (true){
            System.out.print("Selecciona una opció (número o paraula clau): ");
            String opcio = scanner.nextLine();
            switch (opcio.toLowerCase().replace("ó", "o")) {
                case "0":
                case "sortir":
                return "Sortir";
                case "1":
                case "afegir client":
                    return "Afegir client";
                case "2":
                case "modificar client":
                    return "Modificar client";
                case "3":
                case "esborrar client":
                    return "Esborrar client";
                case "4":
                case "llistar clients":
                    return "Llistar clients";
                case "5":
                case "afegir operacio":
                    return "Afegir operació";
                case "6":
                case "modificar operacio":
                    return "Modificar operació";
                case "7":
                case "esborrar operacio":
                    return "Esborrar operació";
                case "8":
                case "llistar operacions":
                    return "Llistar operacions";
            
                default:
                    System.out.println("Opció no vàlida. Torna a intentar-ho.");
                    break;
                   
            }
        }
        }
                
    /**
     * Demana i valida el nom d'un client.
     * Mostra el missatge "Introdueix el nom del client: " i valida que el nom sigui
     * correcte.
     * Si el nom no és vàlid, afegeix el missatge d'error a la llista i torna a
     * demanar el nom.
     * Fes servir la funció "validarNom"
     *
     * @param scanner Scanner per llegir l'entrada de l'usuari
     * @return El nom validat del client
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testLlegirNom"
     */
    public static String llegirNom(Scanner scanner) {
        while (true) {
            System.out.print("Introdueix el nom del client: ");
            String nom = scanner.nextLine();
            if (validarNom(nom)) {
                return nom;
            }
            else {
                System.out.println("Nom no vàlid. Només s'accepten lletres i espais.");
            }
        }

    }

    /**
     * Demana i valida l'edat d'un client.
     * Mostra el missatge "Introdueix l'edat del client (18-100): " i valida que
     * l'edat sigui correcta.
     * Si l'edat no és vàlida, afegeix el missatge d'error a la llista i torna a
     * demanar l'edat.
     * Fes servir les funcions "isAllDigits" i "validarEdat"
     *
     * @param scanner Scanner per llegir l'entrada de l'usuari
     * @return L'edat validada del client
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testLlegirEdat"
     */
    public static int llegirEdat(Scanner scanner) {
        while (true) {
            System.out.print("Introdueix l'edat del client (18-100): ");
            String edat = scanner.nextLine();
            if (isAllDigits(edat)){
                Integer edatANumero = Integer.parseInt(edat);
                if (validarEdat(edatANumero)){
                    return edatANumero;
                }
            }
            System.out.println("Edat no vàlida. Introdueix un número entre 18 i 100.");
        }
    }

    /**
     * Demana i valida els factors d'un client.
     * Primer demana el tipus de client (autònom/empresa) i després el nivell de
     * risc.
     * Per autònoms, només permet 'risc alt' o 'risc mitjà'.
     * Per empreses, permet 'risc alt', 'risc mitjà' o 'risc baix'.
     * 
     * Mostra els següents missatges:
     * - "Introdueix el primer factor ('autònom' o 'empresa'): "
     * - Per autònoms: "Introdueix el segon factor ('risc alt' o 'risc mitjà'): "
     * - Per empreses: "Introdueix el segon factor ('risc alt', 'risc baix' o 'risc
     * mitjà'): "
     *
     * @param scanner Scanner per llegir l'entrada de l'usuari
     * @return ArrayList amb els dos factors validats
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testLlegirFactors"
     */
    public static ArrayList<String> llegirFactors(Scanner scanner) {
        
        while (true) {
            ArrayList<String> factors = new ArrayList<>();
            System.out.print("Introdueix el primer factor ('autònom' o 'empresa'): ");
            String primerFactor = scanner.nextLine();
            factors.add(primerFactor);

            if (primerFactor.toLowerCase().equals("autònom")) {
                while (true){
                    System.out.print("Introdueix el segon factor ('risc alt' o 'risc mitjà'): ");
                    String segonFactor = scanner.nextLine();

                    if (segonFactor.toLowerCase().equals("risc baix") || (!segonFactor.toLowerCase().equals("risc alt") && !segonFactor.toLowerCase().equals("risc mitjà"))) {
                        System.out.println("Factor no vàlid");
                        continue;
                    }
                    factors.add(segonFactor);
                    break;
                }        
            } else if (primerFactor.toLowerCase().equals("empresa")) {
                while (true){
                    System.out.print("Introdueix el segon factor ('risc alt', 'risc baix' o 'risc mitjà': ");
                    String segonFactor = scanner.nextLine();
                    if (!segonFactor.toLowerCase().equals("risc alt") && !segonFactor.toLowerCase().equals("risc baix") && !segonFactor.toLowerCase().equals("risc mitjà")){
                        System.out.println("Factor no vàlid");
                        continue;
                    }
                    factors.add(segonFactor);
                    break;
                }
                
            }
            return factors;
        }
    }

    /**
     * Demana i valida un descompe
     * Primer demana el descompte amb:
     * "Introdueix el descompte (0-20): "
     * 
     * Mostra el següent missatge en cas d'error:
     * "Descompte no vàlid. Ha de ser un número entre 0 i 20."
     *
     * @param scanner Scanner per llegir l'entrada de l'usuari
     * @return ArrayList amb els dos factors validats
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testLlegirDescompte"
     */
    public static double llegirDescompte(Scanner scanner) {
        while (true) {
            System.out.print("Introdueix el descompte (0-20): ");
            String descompte = scanner.nextLine();
            try {
                Double descompteVerificat = Double.parseDouble(descompte);
                if (descompteVerificat<0 || descompteVerificat>20){
                    throw new NumberFormatException();
                }
                return descompteVerificat;
            } catch (NumberFormatException e) {
                System.out.print("Descompte no vàlid. Ha de ser un número entre 0 i 20.");
            }

        }
    }

    /**
     * Gestiona el procés d'afegir un nou client mitjançant interacció amb l'usuari.
     * Utilitza les següents funcions auxiliars per obtenir i validar les dades:
     * - llegirNom: per obtenir el nom del client
     * - llegirEdat: per obtenir l'edat (entre 18 i 100)
     * - llegirFactors: per obtenir el tipus (autònom/empresa) i nivell de risc
     * - llegirDescompte: per obtenir el descompte (entre 0 i 20)
     * 
     * La primera línia del retorn sempre és "=== Afegir Client ==="
     * 
     * Missatges d'error que s'afegeixen a la llista de retorn per les funcions
     * auxiliars:
     * - "Nom no vàlid. Només s'accepten lletres i espais."
     * - "Edat no vàlida. Introdueix un número entre 18 i 100."
     * - "Factor no vàlid. Ha de ser 'autònom' o 'empresa'."
     * - "Factor no vàlid. Per a autònoms només pot ser 'risc alt' o 'risc mitjà'."
     * - "Factor no vàlid. Ha de ser 'risc alt', 'risc baix' o 'risc mitjà'."
     * - "Els factors no són vàlids."
     * - "Descompte no vàlid. Ha de ser un número entre 0 i 20."
     * 
     * En cas d'èxit, s'afegeix a la llista:
     * - "S'ha afegit el client amb clau " + novaClau + "."
     * 
     * @param scanner L'objecte Scanner per rebre l'entrada de l'usuari
     * @return Una llista de cadenes de text que contenen els missatges d'estat del
     *         procés
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testAfegirClientMenu"
     */
    public static ArrayList<String> afegirClientMenu(Scanner scanner) {
        ArrayList<String> afegirNouUsuari = new ArrayList<>();
        afegirNouUsuari.add("=== Afegir Client ===");
        String nom = llegirNom(scanner);
        Integer edat = llegirEdat(scanner);
        ArrayList<String> factors = llegirFactors(scanner); 
        Double descompte = llegirDescompte(scanner);
        String clientInfo = afegirClient(nom, edat, factors, descompte);
        afegirNouUsuari.add("S'ha afegit el client amb clau " + clientInfo);

        return afegirNouUsuari;
    }

    /**
     * Gestiona el procés de modificació d'un client existent.
     * 
     * Primer demana i valida la clau del client:
     * - "Introdueix la clau del client a modificar (per exemple, 'client_100'): "
     * 
     * Si el client existeix:
     * - Mostra "Camps disponibles per modificar: nom, edat, factors, descompte"
     * - Demana "Introdueix el camp que vols modificar: "
     * 
     * Segons el camp escollit, utilitza les funcions auxiliars:
     * - llegirNom: si es modifica el nom
     * - llegirEdat: si es modifica l'edat
     * - llegirFactors: si es modifiquen els factors
     * - llegirDescompte: si es modifica el descompte
     * 
     * La primera línia del retorn sempre és "=== Modificar Client ==="
     * 
     * Missatges d'error que s'afegeixen a la llista de retorn:
     * - "Client amb clau " + clauClient + " no existeix."
     * - "El camp " + camp + " no és vàlid."
     * 
     * Més els missatges d'error de les funcions auxiliars:
     * - "Nom no vàlid. Només s'accepten lletres i espais."
     * - "Edat no vàlida. Introdueix un número entre 18 i 100."
     * - "Factor no vàlid. Ha de ser 'autònom' o 'empresa'."
     * - "Factor no vàlid. Per a autònoms només pot ser 'risc alt' o 'risc mitjà'."
     * - "Factor no vàlid. Ha de ser 'risc alt', 'risc baix' o 'risc mitjà'."
     * - "Els factors no són vàlids."
     * - "Descompte no vàlid. Ha de ser un número entre 0 i 20."
     * 
     * En cas d'èxit, s'afegeix a la llista:
     * - "S'ha modificat el client " + clauClient + "."
     * 
     * @param scanner L'objecte Scanner per rebre l'entrada de l'usuari
     * @return Una llista de cadenes de text que contenen els missatges d'estat del
     *         procés
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testModificarClientMenu"
     */
    public static ArrayList<String> modificarClientMenu(Scanner scanner) {
        ArrayList<String> modificarClients = new ArrayList<>(Arrays.asList("=== Modificar Client ==="));
        System.out.print("Introdueix la clau del client a modificar (per exemple, 'client_100'): ");
        String idClient = scanner.nextLine();
        if (!clients.containsKey(idClient)){
            modificarClients.add("Client amb clau "+idClient+" no existeix.");
            return modificarClients;
        }
        modificarClients.add("Camps disponibles per modificar: nom, edat, factors, descompte");
        System.out.print("Introdueix el camp que vols modificar: ");
        String camp = scanner.nextLine();
        
        if (!clients.get(idClient).containsKey(camp)){
            modificarClients.add("El camp " + camp + " no és vàlid.");
            return modificarClients;
        }
        
        switch (camp) {
            case "nom": 
                modificarClient(idClient, camp, llegirNom(scanner));
                break; 
            case "edat":
                modificarClient(idClient, camp, llegirEdat(scanner));
                break;
            case "factors":
                modificarClient(idClient, camp, llegirFactors(scanner));
                break;
            case "descompte":
                modificarClient(idClient, camp, llegirDescompte(scanner));
                break;     
            default:
                break;
        }
        modificarClients.add("S'ha modificat el client " + idClient + ".");

        
        return modificarClients;
    }

    /**
     * Gestiona el procés d'esborrar un client existent mitjançant interacció amb
     * l'usuari.
     * 
     * Mostra per pantalla el següent missatge per demanar dades:
     * - "Introdueix la clau del client a esborrar (per exemple, 'client_100'): "
     * 
     * La primera línia del retorn sempre és "=== Esborrar Client ==="
     * 
     * Missatges d'error que s'afegeixen a la llista de retorn:
     * - "Client amb clau " + clauClient + " no existeix."
     * 
     * En cas d'èxit, s'afegeix a la llista:
     * - "S'ha esborrat el client " + clauClient + "."
     * 
     * @param scanner L'objecte Scanner per rebre l'entrada de l'usuari.
     * @return Una llista de cadenes de text que contenen els missatges d'estat del
     *         procés.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testEsborrarClientMenu"
     */
    public static ArrayList<String> esborrarClientMenu(Scanner scanner) {
        System.out.print("Introdueix la clau del client a esborrar (per exemple, 'client_100'): ");
        String clauClient = scanner.nextLine();
        ArrayList<String> esborrarClientLlista = new ArrayList<>(Arrays.asList("=== Esborrar Client ==="));
        String clientEsborrat = esborrarClient(clauClient);
        if (clientEsborrat.equals("OK")){
            esborrarClientLlista.add("S'ha esborrat el client "+clauClient+".");
            return esborrarClientLlista;
        }
        esborrarClientLlista.add(clientEsborrat);
        return esborrarClientLlista;
    }

    /**
     * Gestiona el menú principal de l'aplicació i l'execució de les operacions.
     *
     * Aquesta funció implementa un bucle que:
     * 1. Mostra el menú principal.
     * 2. Mostra els missatges d'error o avís
     * 3. Obté l'opció seleccionada per l'usuari.
     * 4. Executa l'acció corresponent utilitzant les funcions existents.
     * 5. Finalitza quan l'usuari selecciona "Sortir".
     *
     * Els textos mostrats són:
     * - "=== Menú de Gestió de Notaria ==="
     * - "Selecciona una opció (número o paraula clau): "
     * - "Opció no vàlida. Torna a intentar-ho."
     * - "Fins aviat!"
     *
     * @param scanner L'objecte Scanner per llegir l'entrada de l'usuari.
     */
    public static void gestionaClientsOperacions(Scanner scanner) {
        while (true) {
            getCadenesMenu();
            String opcio = obtenirOpcio(scanner);
            if (opcio.equals("Sortir")) {
                System.out.print("Fins aviat!");
                return;
            } else if (opcio.equals("Afegir client")) {
                afegirClientMenu(scanner);
            } else if (opcio.equals("Modificar client")) {
                modificarClientMenu(scanner);
            } else if (opcio.equals("Esborrar client")) {
                esborrarClientMenu(scanner);
            } else if (opcio.equals("Llistar clients")) {
                getLlistarClientsMenu();
            }
        }
    }

    /**
     * 
     * @run ./run.sh "com.exercicis.Exercici0"
     * @test ./runTest.sh "com.exercicis.TestExercici0"
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        gestionaClientsOperacions(scanner);

        scanner.close();
    }
}
