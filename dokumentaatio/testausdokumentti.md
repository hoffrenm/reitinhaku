# Sovelluksen testaus

Sovelluksen ydintoiminnallisuutta, eli A*- ja JPS-algoritmien, heuristiikan sekä prioriteettijonon toimintaa on testattu kattavasti yksikkötesteillä. Algoritmien testit testaavat myös sovelluksen muiden osien toimintaa yhdessä, joten ne ajavat siten myös integraatiotestien virkaa. Käyttöliittymä on jätetty testauksen ulkopuolelle. Kaikkiaan dataobjekteja sekä logiikkaluokkia käsittelevien testien rivi- ja haaraumakattavuus on yli 90%.

![testauskattavuus](https://github.com/hoffrenm/reitinhaku/blob/master/dokumentaatio/images/codecov.png)

# Suorituskykyvertailu

## Algoritmien tuloksien testaaminen

Algoritmien selvittämien lyhimpien polkujen oikeellisuus on varmistettu suorittamalla [Moving Ai Lab](https://movingai.com/benchmarks/grids.html) sivuston tarjoamilla kartoilla ja niihin liittyvillä skenaarioilla. [Skenaariot](https://movingai.com/benchmarks/formats.html) sisältävät tuhansia lähtö- ja maalipareja, joiden välinen lyhin etäisyys on ilmoitettu. Tässä harjoitustyössä oletan, että skenaariossa ilmoitettu lyhin polku on oikea ja sitä verrataan algoritmien selvittämään etäisyyteen.

### Huomio etäisyyden laskemisesta

Skenaarion lyhin etäisyys on ilmoitettu siten, ettei nurkan yli saa liikkua. Tämän harjoitustyön algoritmitmeille se on kuitenkin sallittua, josta seuraa epätarkkuutta (nurkan ohittaminen = 2 yksikköä vs neliöjuuri(2) yksikköä). Tämä on otettu huomioon testatessa.

## Testausasetelma

Tavoitteena oli vertailla A* ja Jump Point Searchin tehokkuutta erilaisilla kartoilla. Pienemmissä kartoissa mukana on myös A* ilman heuristiikka vertailukohtana. Heuristiikkana käytetään muutoin euklidista etäisyyttä koska se osoittautui käyttökelpoisimmaksi useimmissa tilanteissa. Algoritmit selvittivät peräkkäin samat tapaukset ja siihen kulunut suoritusaika, käytyjen solmujen määrä ja reitin pituus otettiin talteen. Valitsin kolme erilaista karttaa, joiden toivoin tuovan esille algoritmien toimintaa mahdollisimman kuvaavasti.

### 32room_001
- 2060 testiä
- etäisyydet välillä 1 - 821
- Kartassa solmuja yhteensä 245 463
- [Kuva kartasta](https://movingai.com/benchmarks/room/32room_001.png)

#### Suoritusaika vs löydetyn reitin pituus

![time vs distance](https://github.com/hoffrenm/reitinhaku/blob/master/dokumentaatio/images/room32_001_length.png)

#### Tutkitut solmut vs löydetyn reitin pituus

![nodes vs distance](https://github.com/hoffrenm/reitinhaku/blob/master/dokumentaatio/images/room32_001_nodes.png)

Verrokkina oleva A* ilman heuristiikkaa päätyy usein tutkimaan suuren osan graafista ennen maalin löytymistä. Heuristiikan ansiosta A* osaa suunnata hakuaan maalia kohti ja onnistuu saavuttamaan maalisolmun huomattavasti nopeammin useimmissa tapauksissa. Jump point search toimii täysin omassa nopeusluokassaan. Huomioitavaa on myös se, ettei JPS kierrätä valtaosaa tarkasteltavista solmuista minimikeon kautta vaan hylkää ne karsintasääntöjen mukaisesti.

### maze512_16_0
- 9130 testiä
- etäisyydet välillä 1 - 3649
- Kartassa solmuja yhteensä 246 016
- [Kuva kartasta](https://movingai.com/benchmarks/maze/maze512-16-0.png)

![time vs distance](https://github.com/hoffrenm/reitinhaku/blob/master/dokumentaatio/images/maze512length.png)

A*:n suoriutuminen sokkelossa muistuttaa hyvin paljon edellisen kartan "tyhmää A*:ia". Tämä johtuu luultavasti siitä, että heuristiikka ei varsinaisesti ohjaa algoritmin toimintaa oikeaan suuntaan vaan reitinhaku ajautuu usein umpikujaan. Heuristiikan valinnalla voidaan merkittävästi vaikuttaa A*:n tehokkuuteen ja toisaalta tietynlainen kartta hävittää heuristiikasta saatavan hyödyn täysin. JPS puolestaan toimii takuuvarmasti nopeasti koska kartassa on paljon vaaka- ja pystysuunnan siirtymiä.

### Paris_1_1024
- 3760 testiä
- Etäisyydet välillä 1 - 1503
- Kartassa solmuja yhteensä 800 729
- [Kuva kartasta](https://movingai.com/benchmarks/street/Paris_1_1024.png)

![time vs distance](https://github.com/hoffrenm/reitinhaku/blob/master/dokumentaatio/images/paris1024length.png)

Suuremmalla solmumäärällä ja suhkot avoimella kartalla on huomattavissa eksponentiaalista kasvua. Vaikka lopullisen reitin pituus ei ole merkittävästi suurempi, vaikuttaa lisääntynyt solmujen määrä radikaalista kummankin algoritmin suoritusaikaan.
