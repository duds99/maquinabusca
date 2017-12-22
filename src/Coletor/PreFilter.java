/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coletor;

import java.io.IOException;
import java.net.MalformedURLException;
import org.jsoup.Jsoup;

/**
 *
 * @author eduardofigueiredo
 */
public class PreFilter {
    Collector coletor;
    public PreFilter() throws IOException{
        coletor = new Collector();
    }
    
    public void LivingPeople()throws MalformedURLException, IOException{
        String aux = "https://en.wikipedia.org/wiki/Category:Living_people?from=0";
        coletor.getContent(aux);
        boolean continua = true;
        int cont = 0;
        while(continua = true){
            org.jsoup.nodes.Document doc =  Jsoup.connect(aux).get();
            //acessa div central
            org.jsoup.select.Elements link = doc.select("#mw-pages");
            for(int i=0;i<link.size();i++){
                //pega next page para coletar mais links de pessoas vivas
                if(link.get(i).text().equals("next page")){
                    aux = link.get(i).attr("href");
                    if(aux.length()>0){
                        i = link.size();
                        cont = 1;
                    }else{
                        cont = 0;
                    }
                }else{
                    cont = 0;
                }
            }
            
            if(cont == 1){
                coletor.getContent(aux);
            }else{
                continua = false;
            }
        }
    }
    
    //links wikipedia pessoas mortas
    public void DeadPeople() throws IOException{
        for(int i = 1940;i<=2017;i++){
            for(int j=0;j<26;j++){
                int a = j+97;
                char primeiro = (char)a;
                coletor.getContent("https://en.wikipedia.org/w/index.php?title=Category:" + i + "_deaths&from=" +primeiro);
            }    
        }
    }
    
    //link de paizes wikipedia
    public void Country() throws IOException{
        coletor.getContent("https://en.wikipedia.org/wiki/List_of_sovereign_states_and_dependent_territories_in_Africa");
        coletor.getContent("https://en.wikipedia.org/wiki/Territorial_claims_in_Antarctica");
        coletor.getContent("https://en.wikipedia.org/wiki/List_of_sovereign_states_and_dependent_territories_in_Asia");
        coletor.getContent("https://en.wikipedia.org/wiki/List_of_sovereign_states_and_dependent_territories_in_Europe");
        coletor.getContent("https://en.wikipedia.org/wiki/List_of_sovereign_states_and_dependent_territories_in_North_America");
        coletor.getContent("https://en.wikipedia.org/wiki/List_of_sovereign_states_and_dependent_territories_in_Oceania");
        coletor.getContent("https://en.wikipedia.org/wiki/List_of_sovereign_states_and_dependent_territories_in_South_America");
    }
    
    //lista d ecidades wikipedia
    public void Cities() throws IOException{
        String matriz[] = new String[]{"AFGHANISTAN","AKROTIRI E DEKÉLIA","SOUTH AFRICA","ALBANIA","GERMANY","AMERICAN SAMOA","ANDORRA","ANGOLA","ANGUILLA","ANTIGUA AND BARBUDA","NETHERLANDS ANTILLES","SAUDI ARABIA","ALGERIA","ARGENTINA","ARMENIA","ARUBA","AUSTRALIA","AUSTRIA","AZERBAIJAN","BAHAMAS","BANGLADESH","BARBADOS","BAHRAIN","BASSAS DA INDIA","BELGIUM","BELIZE","BENIN","BERMUDA","BELARUS","BOLIVIA","BOSNIA AND HERZEGOVINA","BOTSWANA","BRAZIL","BRUNEI DARUSSALAM","BULGARIA","BURKINA FASO","BURUNDI","BHUTAN","CAPE VERDE","CAMEROON","CAMBODIA","CANADA","QATAR","KAZAKHSTAN","CENTRAL AFRICAN REPUBLIC","CHAD","CHILE","CHINA","CYPRUS","COLOMBIA","COMOROS","CONGO","CONGO DEMOCRATIC REPUBLIC","KOREA NORTH","KOREA SOUTH","IVORY COAST","COSTA RICA","CROATIA","CUBA","DENMARK","DOMINICA","EGYPT","UNITED ARAB EMIRATES","ECUADOR","ERITREA","SLOVAKIA","SLOVENIA","SPAIN","UNITED STATES","ESTONIA","ETHIOPIA","GAZA STRIP","FIJI","PHILIPPINES","FINLAND","FRANCE","GABON","GAMBIA","GHANA","GEORGIA","GIBRALTAR","GRENADA","GREECE","GREENLAND","GUADELOUPE","GUAM","GUATEMALA","GUERNSEY","GUYANA","FRENCH GUIANA","GUINEA","EQUATORIAL GUINEA","GUINEA-BISSAU","HAITI","HONDURAS","HONG KONG","HUNGARY","YEMEN","BOUVET ISLAND","CHRISTMAS ISLAND","CLIPPERTON ISLAND","JUAN DE NOVA ISLAND","ISLE OF MAN","NAVASSA ISLAND","EUROPA ISLAND","NORFOLK ISLAND","TROMELIN ISLAND","ASHMORE AND CARTIER ISLANDS","CAYMAN ISLANDS","COCOS (KEELING) ISLANDS","COOK ISLANDS","CORAL SEA ISLANDS","FALKLAND ISLANDS (ISLAS MALVINAS)","FAROE ISLANDS","SOUTH GEORGIA AND THE SOUTH SANDWICH ISLANDS","NORTHERN MARIANA ISLANDS","MARSHALL ISLANDS","PARACEL ISLANDS","PITCAIRN ISLANDS","SOLOMON ISLANDS","SPRATLY ISLANDS","UNITED STATES VIRGIN ISLANDS","BRITISH VIRGIN ISLANDS","INDIA","INDONESIA","IRAN","IRAQ","IRELAND","ICELAND","ISRAEL","ITALY","JAMAICA","JAN MAYEN","JAPAN","JERSEY","DJIBOUTI","JORDAN","KIRIBATI","KUWAIT","LAOS","LESOTHO","LATVIA","LEBANON","LIBERIA","LIBYAN ARAB JAMAHIRIYA","LIECHTENSTEIN","LITHUANIA","LUXEMBOURG","MACAO","MACEDONIA","MADAGASCAR","MALAYSIA","MALAWI","MALDIVES","MALI","MALTA","MOROCCO","MARTINIQUE","MAURITIUS","MAURITANIA","MAYOTTE","MEXICO","MYANMAR BURMA","MICRONESIA","MOZAMBIQUE","MOLDOVA","MONACO","MONGOLIA","MONTENEGRO","MONTSERRAT","NAMIBIA","NAURU","NEPAL","NICARAGUA","NIGER","NIGERIA","NIUE","NORWAY","NEW CALEDONIA","NEW ZEALAND","OMAN","NETHERLANDS","PALAU","PALESTINE","PANAMA","PAPUA NEW GUINEA","PAKISTAN","PARAGUAY","PERU","FRENCH POLYNESIA","POLAND","PUERTO RICO","PORTUGAL","KENYA","KYRGYZSTAN","UNITED KINGDOM","CZECH REPUBLIC","DOMINICAN REPUBLIC","ROMANIA","RWANDA","RUSSIAN FEDERATION","WESTERN SAHARA","EL SALVADOR","SAMOA","SAINT HELENA","SAINT LUCIA","HOLY SEE","SAINT KITTS AND NEVIS","SAN MARINO","SAINT PIERRE AND MIQUELON","SAO TOME AND PRINCIPE","SAINT VINCENT AND THE GRENADINES","SEYCHELLES","SENEGAL","SIERRA LEONE","SERBIA","SINGAPORE","SYRIA","SOMALIA","SRI LANKA","SWAZILAND","SUDAN","SWEDEN","SWITZERLAND","SURINAME","SVALBARD","THAILAND","TAIWAN","TAJIKISTAN","TANZANIA","BRITISH INDIAN OCEAN TERRITORY","HEARD ISLAND AND MCDONALD ISLANDS","TIMOR-LESTE","TOGO","TOKELAU","TONGA","TRINIDAD AND TOBAGO","TUNISIA","TURKS AND CAICOS ISLANDS","TURKMENISTAN","TURKEY","TUVALU","UKRAINE","UGANDA","URUGUAY","UZBEKISTAN","VANUATU","VENEZUELA","VIETNAM","WALLIS AND FUTUNA","ZAMBIA","ZIMBABWE"};
        for(int i=0;i<252;i++){
            matriz[i] = matriz[i].replace(" ","_");
            matriz[i] = matriz[i].charAt(0) + "" +  matriz[i].substring(1,matriz[i].length()).toLowerCase();
            coletor.getContent("https://en.wikipedia.org/wiki/List_of_cities_in_" + matriz[i]);
        }
    }
    
}
