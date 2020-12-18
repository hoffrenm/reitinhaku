# Käyttöohje

## Sovelluksen käynnistäminen

Lataa uusimman [julkaisun](https://github.com/hoffrenm/reitinhaku/releases) reitinhaku.jar tiedosto

Suorita seuraava komento komentorivillä ladatun jar-tiedoston hakemistossa.
```
java -jar reitinhaku.jar
```

## Sovelluksen käyttö

Sovellus käynnistyy seuraavanlaiseen ikkunaan

![]()

### Kartan tuominen

Painamalla "Lataa kartta" aukeaa tiedostovalitsin, jolla sovellukseen voidaan tuoda kuva kartasta. 

### Reitinhaku

#### Lähtö- ja maalipisteet
- Koordinaatit voi syöttää niille varattuihin kenttiin

tai

- Lähtöpisteen voi asettaa klikkaamalla karttaa hiiren vasemmalla painikkeella
- Maalipisteen voi asettaa klikkaamalla karttaa hiiren oikealla painikkeella

#### Algoritmin ja heuristiikan valinta
- Valitse haluamasi algoritmi ja heuristiikkafunktio valintanappien avulla

Klikkaamalla "Selvitä reitti" sovellus ratkaisee ja piirtää löydetyn reitin punaisella ja värittää läpikäydyt solmut sinisellä. Voit toistaa reitinhaun uusilla maali- ja alkupisteillä sekä vaihtaa algoritmia ja heuristiikkaa. Vain edeltävä reitinhaun tulos on esitettynä kartalle yhdellä kertaa.

## Suorituskykytestauksen käyttö
