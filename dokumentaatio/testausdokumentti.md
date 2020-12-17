# Sovelluksen testaus

Sovelluksen ydintoiminnallisuutta, eli A*- ja JPS-algoritmien, heuristiikan sekä prioriteettijonon toimintaa on testattu kattavasti yksikkötesteillä. Algoritmien testit testaavat myös sovelluksen muiden osien toimintaa yhdessä, joten ne ajavat siten myös integraatiotestien virkaa. Käyttöliittymä on jätetty testauksen ulkopuolelle. Kaikkiaan dataobjekteja sekä logiikkaluokkia käsittelevien testien rivi- ja haaraumakattavuus on yli 90%.

![testauskattavuus](https://github.com/hoffrenm/reitinhaku/blob/master/dokumentaatio/images/codecov.png)

# Suorituskykyvertailu

## Algoritmien tuloksien testaaminen

Algoritmien selvittämien lyhimpien polkujen oikeellisuus on varmistettu suorittamalla [Moving Ai Lab](https://movingai.com/benchmarks/grids.html) sivuston tarjoamilla kartoilla ja niihin liittyvillä skenaarioilla. [Skenaariot](https://movingai.com/benchmarks/formats.html) sisältävät tuhansia lähtö- ja maalipareja, joiden välinen lyhin etäisyys on ilmoitettu. Tässä harjoitustyössä oletan, että skenaariossa ilmoitettu lyhin polku on oikea ja sitä verrataan algoritmien selvittämään etäisyyteen.

### Huomio etäisyyden laskemisesta

Skenaarion lyhin etäisyys on ilmoitettu siten, ettei nurkan yli saa liikkua. Tämän harjoitustyön algoritmitmeille se on kuitenkin sallittua, josta seuraa epätarkkuutta (nurkan ohittaminen = 2 yksikköä vs neliöjuuri(2) yksikköä). Tämä on otettu huomioon testatessa.

## Testausasetelma

Tavoitteena oli vertailla A* ja Jump Point Searchin tehokkuutta erilaisilla kartoilla. Pienemmissä kartoissa mukana on myös A* ilman heuristiikka vertailukohtana. Heuristiikkana käytetään muutoin euklidista etäisyyttä koska se osoittautui käyttökelpoisimmaksi useimmissa tilanteissa. Algoritmit selvittivät peräkkäin samat tapaukset ja siihen kulunut suoritusaika, käytyjen solmujen määrä ja reitin pituus otettiin talteen.

### 32room_001 [kuva](https://movingai.com/benchmarks/room/32room_001.png)
- 2060 testiä
- etäisyydet välillä 1 - 821

Suoritusaika vs löydetyn reitin pituus
![time vs distance](https://github.com/hoffrenm/reitinhaku/blob/master/dokumentaatio/images/room32_001_length.png)
Tutkitut solmut vs löydetyn reitin pituus
![nodes vs distance](https://github.com/hoffrenm/reitinhaku/blob/master/dokumentaatio/images/room32_001_nodes.png)

### maze512_16_0 [kuva](https://movingai.com/benchmarks/maze/maze512-16-0.png)
- 9130 testiä
- etäisyydet välillä 1 - 3649

![time vs distance](https://github.com/hoffrenm/reitinhaku/blob/master/dokumentaatio/images/maze512length.png)
