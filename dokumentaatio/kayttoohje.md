# Käyttöohje

## Sovelluksen käynnistäminen

Lataa uusimman [julkaisun](https://github.com/hoffrenm/reitinhaku/releases) reitinhaku.jar tiedosto

Suorita seuraava komento komentorivillä ladatun jar-tiedoston hakemistossa.
```
java -jar reitinhaku.jar
```

## Sovelluksen käyttö

Sovellus käynnistyy seuraavanlaiseen ikkunaan

![Aloitus](https://github.com/hoffrenm/reitinhaku/blob/master/dokumentaatio/images/img4.PNG)

### Kartan tuominen

Painamalla "Lataa kartta" aukeaa tiedostovalitsin, jolla sovellukseen voidaan tuoda kuva kartasta. Kartan koolle ei ole asetettu rajoitteita. Kaikki pikselit harmaasta valkoiseen tulkitaan solmuiksi ja loput pikselit ovat seiniä.

### Reitinhaku

#### Lähtö- ja maalipisteet
- Koordinaatit voi syöttää niille varattuihin kenttiin

tai

- Lähtöpisteen voi asettaa klikkaamalla karttaa hiiren vasemmalla painikkeella
- Maalipisteen voi asettaa klikkaamalla karttaa hiiren oikealla painikkeella

#### Algoritmin ja heuristiikan valinta
- Valitse haluamasi algoritmi ja heuristiikkafunktio valintanappien avulla

Klikkaamalla "Selvitä reitti" sovellus ratkaisee ja piirtää löydetyn reitin punaisella ja värittää läpikäydyt solmut sinisellä. Voit toistaa reitinhaun uusilla maali- ja alkupisteillä sekä vaihtaa algoritmia ja heuristiikkaa. Vain edeltävä reitinhaun tulos on esitettynä kartalle yhdellä kertaa.

![Reitinhaku](https://github.com/hoffrenm/reitinhaku/blob/master/dokumentaatio/images/img5.PNG)

## Suorituskykytestauksen käyttö

### Empiirinen testaus

Empiiristä testausta voi tehdä käsin sovelluksen kautta.

### Skenaarioiden suorittaminen

Voit ladata eri karttojen skenaarioita [Moving AI Lab](https://movingai.com/benchmarks/grids.html) sivustolta.
Sovelluksen suoritushakemistossa (sama hakemisto, jossa jar-tiedosto sijaitsee) täytyy olla kansio _maps_, jonka sisälle _kartta_.map ja _kartta_.map.scen täytyy sijoittaa. Mikäli kloonasit repositorion tämä kansio on jo olemassa.

Suorita reitinhaku.jar tiedosto parametrilla (tai anna IDE:lle suoritusparametriksi)
<pre>
java -jar reitinhaku.jar <i>kartta</i>
</pre>

Sovellus suorittaa _kartta_.map.scen tiedostossa olevat reitinhaut ja tallettaa tulokset suoritushakemistoon _kartta_.txt-tiedostoon. Nykyisessä muodossa sovellus testaa kaikki parit A*- ja JPS-algoritmeilla.

Tulokset tallettuvat csv-formaatissa seuraavasti
```
algoritmi;aika;pituus;tutkitutsolmut
```

Yksi rivi käsittää yhden tuloksen ja tyypillisesti skenaario sisältää tuhansia pareja. Täten suoritusaika voi vaihdella joistain minuuteista useisiin kymmeniin riippuen kartan haastavuudesta. Tulosten visualisointiin voit käyttää mieleistäsi datan visualisointityökalua.

[Testausdokumentissa](https://github.com/hoffrenm/reitinhaku/blob/master/dokumentaatio/testausdokumentti.md#suorituskykyvertailu) tarkemmin tuloksista ja rajoitteista.

### Huom

Graafisen käyttöliittymän kautta annetun kartan täytyy olla kuva ja benchmarkkaukseen sopivat tiedostot ovat tekstitiedostoja.
