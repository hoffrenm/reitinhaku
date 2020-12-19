# Toteutusdokumentti
## Sovelluksen rakenne

![Pakkauskaavio](https://github.com/hoffrenm/reitinhaku/blob/master/dokumentaatio/images/packagediagram.PNG)

Sovellusta voi suorittaa kahdella eri tavalla
- Käynnistämällä sovellus ja käyttämällä sitä graafisen käyttöliittymän kautta
- Suorittaa skenaarioita eli benchmarkata eri karttoja

Näistä kerrottu tarkemmin [käyttöohjeessa.](https://github.com/hoffrenm/reitinhaku/blob/master/dokumentaatio/kayttoohje.md)

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
| Keskimäärin              |        |

Algoritmin suorituksen aikana solmut käydään läpi ottamalla se minimikeosta, tähän kuluu aikaa siis `V log(V)`. Jokaista solmua kohden käydään läpi sen vieruslista ja lisätään ne minimikekoon, tähän kuluu aikaa `E log(V)`. Jos kaikki solmut ja vieruslistat käydään läpi niin aikaa kuluu yhteensä `V log(V) + E log(V) = (E + V) log(V)`. Huonoimmassa tapauksessa A*:n suorituskyky lähestyy Djikstran-algoritmin suorituskykyä.

Tässä toteutuksessa verkko on suhteellisen harva (solmulla enintään 8 kaarta), jonka takia edellinen arvio on yläraja ja toteutuva aikavaativuus moninverroin parempi. Heuristiikka ohjaa reitinhakua yleensä oikeaan suuntaan, jolloin kekoon lisättyjen ja tarkasteltavien solmujen määrä on huomattavasti rajoitetumpi.

### [JPS](https://github.com/hoffrenm/reitinhaku/blob/master/Reitinhaku/src/main/java/reitinhaku/logics/JPS.java)
| tapaus                   | Aikavaativuus      |
| ------------------------ |:------------------:|
| Keskimäärin              |      |

Jump point searchiin pätee teoriassa samat päättelyt kuin A*:iin koska algoritmit ovat rakenteeltaan samankaltaiset. JPS kuitenkin karsii huomattavan määrän lisättävistä solmuista rekursiivisessa funktiossa, jolloin minimikekoa käsitellään suhteellisen harvoin.

Päätös solmun hylkäämisestä on jump point searchissa lähempänä vakioaikaista operaatiota toisin kuin A*:ssa, jossa solmu kierrätetään aina minimikeon kautta. Toisin sanoen A* päätyy tutkimaan useita symmetrisiä polkuja "turhaan" siinä missä JPS löytää yhden optimaalisen polun kahden solmun välillä, eikä tutki vaihtoehtoisia reittejä.

## Suorituskykyvertailu

Testattuani algoritmeja useilla eri kartoilla JPS selvittää reitin usein 5-20 kertaa nopeammin kuin A* samalla heuristiikalla. Itse heuristiikan vaikutus JPS:n nopeuteen on hyvin vähäinen.

Tarkemmin suorituskykyvertailusta [testausdokumentissa.](https://github.com/hoffrenm/reitinhaku/blob/master/dokumentaatio/testausdokumentti.md#testausasetelma)

# Lähteet
- [Canonical Orderings on Grids](https://web.cs.du.edu/~sturtevant/papers/SturtevantRabin16), Nathan R. Sturtevant, Steve Rabin
