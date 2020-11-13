# Viikko 3

## Tehtyä

Projekti lähti oikeastaan vasta käyntiin viikolla 3
- Aiheen tarkennus A* ja JPS-algoritmit käsittäväksi
- A*-algoritmi toimintakuntoon (Toimiva mutta vielä virittelyä vaativa)
- Valtaosa ajasta meni käyttöliittymän kehitykseen
  - Kartan tuonti sovellukseen
  - Alku- ja maalipisteen valinta
  - Reitin ratkaisu valitulla algoritmilla (toistaiseksi A*)
  - Reitin piirto kartalle mikäli sellainen on olemassa
- Graafin luonti tuodusta kartasta
  - kartaksi kelpaa mikä tahansa kuva, jossa kaikki pikselit valkoisesta harmaaseen tulkitaan solmuiksi ja muut värit seiniksi
  
  ![Viikko3 edistyminen](https://github.com/hoffrenm/reitinhaku/blob/master/dokumentaatio/images/img1.PNG)


## Haasteita
- Konfigurointiongelmat windowsin ja linuxin välillä
- Käyttöliittymän kehitys melkein valmiiksi oli käytännössä edellytys koska algoritmin toiminnan testaamista varten sovellukseen piti saada tuotua mielekkäitä karttoja.

## Seuraavan viikon tavoitteet

- JPS-algoritmin toteuttaminen
- PriorityQueuen oma toteutus
- Yksikkötestien aloitus
- Javadocs aloitus
- Tehokkuustestausta

## Avoimia kysymyksiä
- Graafiesitys vs 2d taulukko. Tällä hetkellä kuvasta luodaan graafi, jossa yksi pikseli on solmu ja sen viereiset (hyväksyttävät) pikselit sen naapureita. Jokaisella 
solmulla on siis koordinaatit ja solmu tietää naapurinsa. JPS-algoritmin yhteydessä puhutaan usein taulukosta mutta tämähän ei pitäisi olla graafiesityksessä ongelma kunhan 
tiedetään seuraavan naapurin suunta, jotta hyppy voidaan suorittaa?
- Liikkuminen 4:ään vs 8:aan suuntaan. Nyt solmulla on maksimissaan 4 naapuria (sivuilla, ala- ja yläpuolella). Lienee kuitenkin järkevää ottaa huomioon liikkuminen myös
diagonaalisesti?

#### Viikolla 3 käytetty aika: 38 tuntia
