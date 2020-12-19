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

Taulukkopohjaisen minikeon tilavaativuus on `O(n)`


### [A*](https://github.com/hoffrenm/reitinhaku/blob/master/Reitinhaku/src/main/java/reitinhaku/logics/AStar.java)
| tapaus                   | Aikavaativuus      | Tilavaativuus |
| ------------------------ |:------------------:|:-------------:|
| Huonoimmassa tapauksessa | _O_((E + V) log V) | O(V)          |
| Keskimääräinen arvio     | _O_(E + (V log V)) | O(V)          |

Algoritmin suorituksen aikana solmut käydään läpi ottamalla se minimikeosta, tähän kuluu aikaa siis `V log(V)`. Jokaista solmua kohden käydään läpi sen vieruslista ja lisätään ne minimikekoon, tähän kuluu aikaa `E log(V)`. Jos kaikki solmut ja vieruslistat käydään läpi niin aikaa kuluu yhteensä `V log(V) + E log(V) = (E + V) log(V)`. Huonoimmassa tapauksessa A*:n suorituskyky lähestyy Djikstran-algoritmin suorituskykyä.

Tässä toteutuksessa verkko on suhteellisen harva (solmulla enintään 8 kaarta), jonka takia edellinen arvio on yläraja ja usein toteutuva aikavaativuus moninverroin parempi. Heuristiikka ohjaa reitinhakua yleensä oikeaan suuntaan, jolloin kekoon lisättyjen ja tarkasteltavien solmujen määrä on huomattavasti rajoitetumpi, joten jos kartta ja heuristiikka ovat yhteensopivat (eli keosta otetaan seuraavaksi solmu, joka on suurella todennäköisyydellä reitillä) voisi aikavaativuus olla jopa `O(E + (V log V))` (ja sitä parempikin).

### [JPS](https://github.com/hoffrenm/reitinhaku/blob/master/Reitinhaku/src/main/java/reitinhaku/logics/JPS.java)
| tapaus                   | Aikavaativuus      | Tilavaativuus |
| ------------------------ |:------------------:|:-------------:|
| Huonoimmassa tapauksessa | _O_((E + V) log V) | O(V)          |
| Keskimääräinen arvio     | _O_(E + (V log V)) | O(V)          |

Jump point searchiin pätee teoriassa samat päättelyt kuin A*:iin koska algoritmit ovat rakenteeltaan samankaltaiset. JPS kuitenkin karsii huomattavan määrän lisättävistä solmuista rekursiivisessa funktiossa, jolloin minimikekoa käsitellään suhteellisen harvoin. 

Päätös solmun hylkäämisestä on jump point searchissa lähempänä vakioaikaista operaatiota toisin kuin A*:ssa, jossa solmu kierrätetään minimikeon kautta. Toisin sanoen A* päätyy tutkimaan useita symmetrisiä polkuja "turhaan" siinä missä JPS löytää yhden optimaalisen polun hyppypisteiden välillä, eikä tutki vaihtoehtoisia reittejä. Lisäksi JPS lisää yhden solmun ainoastaan kerran kekoon algoritmin suorituksen aikana.

### Yleisesti

Algoritmeille on vaikeaa antaa täsmällistä aikavaativuutta. Lopullinen aikavaativuus riippuu itse kartasta sekä hyvin paljon heuristiikasta, jonka avulla algoritmi valikoi seuraavaksi keosta otettavan solmun. A* palautuu Djikstran algoritmin tasolle mikäli heuristiikka ei ole karttaan sopiva ja toisaalta JPS toimii tehokkaasti myös kartoissa, jotka ovat sille teoriassa haastavia.

## Suorituskykyvertailu

Testattuani algoritmeja useilla eri kartoilla JPS selvittää reitin usein 5-20 kertaa nopeammin kuin A* samalla heuristiikalla. Itse heuristiikan vaikutus JPS:n nopeuteen on hyvin vähäinen.

Tarkemmin suorituskykyvertailusta [testausdokumentissa.](https://github.com/hoffrenm/reitinhaku/blob/master/dokumentaatio/testausdokumentti.md#testausasetelma)

## Parannettavaa

- Käyttöliittymä on yhdessä tiedostossa eikä noudata kovin hyvää koodaustapaa.
- Kenties järkevämpi tapa vertailla algoritmeja olisi olisi ollut esimerkiksi iteraatioiden määrä koska heuristiikka ja kartan luonne vaikuttavat merkittävästi algoritmien toimintaa.

# Lähteet
- [Canonical Orderings on Grids](https://web.cs.du.edu/~sturtevant/papers/SturtevantRabin16), Nathan R. Sturtevant, Steve Rabin
- [A* search algorithm](https://en.wikipedia.org/wiki/A*_search_algorithm#cite_note-1), Wikipedia
- [JPS Algorithm Adaptation and Optimization to Three-dimensional Space](https://www.utupub.fi/bitstream/handle/10024/148054/DI_tyo_Pertti_Ranttila_final.pdf?sequence=1&isAllowed=y), Pertti Ranttila
- [Amit’s A* Pages](http://theory.stanford.edu/~amitp/GameProgramming/)
- [The JPS Pathfinding System](https://www.aaai.org/ocs/index.php/SOCS/SOCS12/paper/viewFile/5396/5212), Daniel Haraborand, Alban Grastien
