# Toteutusdokumentti
## Sovelluksen rakenne

TODO

## Algoritmien aika- ja tilavaativuudet

### [Prioriteettijono](https://github.com/hoffrenm/reitinhaku/blob/master/Reitinhaku/src/main/java/reitinhaku/logics/PriorityQueue.java)
| operaatio                       | Aikavaativuus     |
| ------------------------------- |:-----------------:|
| Alkion lisääminen               | _O_(log n)        |
| Pienimmän alkion haku ja poisto | _O_(log n)        |


### [A*](https://github.com/hoffrenm/reitinhaku/blob/master/Reitinhaku/src/main/java/reitinhaku/logics/AStar.java)
| tapaus                   | Aikavaativuus      |
| ------------------------ |:------------------:|
| Huonoimmassa tapauksessa | _O_((E + V) log V) |
| Keskimäärin              | _O_(E log V)       |

Algoritmin suorituksen aikana solmut käydään läpi ottamalla se minimikeosta, tähän kuluu aikaa siis `V log(V)`. Jokaista solmua kohden käydään läpi sen vieruslista ja lisätään ne minimikekoon, tähän kuluu aikaa `E log(V)`. Jos kaikki solmut ja vieruslistat käydään läpi niin aikaa kuluu yhteensä `V log(V) + E log(V) = (E + V) log(V)`. Huonoimmassa tapauksessa A*:n suorituskyky lähestyy Djikstran-algoritmin suorituskykyä.

Tässä toteutuksessa verkko on suhteellisen harva (solmulla enintään 8 kaarta), jonka takia keskimääräinen aikavaativuus on useimmissa tapauksissa parempi arvio. Heuristiikka ohjaa reitinhakua yleensä oikeaan suuntaan, jolloin kekoon lisättyjen ja tarkasteltavien solmujen määrä on sitäkin rajoitetumpi.

### [JPS](https://github.com/hoffrenm/reitinhaku/blob/master/Reitinhaku/src/main/java/reitinhaku/logics/JPS.java)
| tapaus                   | Aikavaativuus      |
| ------------------------ |:------------------:|
| Keskimäärin              | _O_(E log V)       |

Jump point searchiin pätee teoriassa samat päättelyt kuin A*:iin koska algoritmit ovat rakenteeltaan samankaltaiset. JPS kuitenkin karsii huomattavan määrän lisättävistä solmuista rekursiivisessa funktiossa, jolloin minimikekoa käsitellään suhteellisen harvoin.

Päätös solmun hylkäämisestä on jump point searchissa lähempänä vakioaikaista operaatiota toisin kuin A*:ssa, jossa solmu kierrätetään aina minimikeon kautta. Toisin sanoen A* päätyy tutkimaan useita symmetrisiä polkuja "turhaan" siinä missä JPS löytää yhden optimaalisen polun kahden solmun välillä, eikä tutki vaihtoehtoisia reittejä.

## Suorituskykyvertailu

Testattuani algoritmeja useilla eri kartoilla JPS selvittää reitin usein 5-20 kertaa nopeammin kuin A* samalla heuristiikalla. Itse heuristiikan vaikutus JPS:n nopeuteen on hyvin vähäinen.

Tarkemmin suorituskykyvertailusta [testausdokumentissa.](https://github.com/hoffrenm/reitinhaku/blob/master/dokumentaatio/testausdokumentti.md#testausasetelma)

# Lähteet
- [Canonical Orderings on Grids](https://web.cs.du.edu/~sturtevant/papers/SturtevantRabin16), Nathan R. Sturtevant, Steve Rabin
