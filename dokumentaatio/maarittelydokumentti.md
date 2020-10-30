# Määrittelydokumentti

Opinto-ohjelma tietojenkäsittelytieteen kanditaatti (TKT).

Dokumentaation kieli on suomi ja ohjelmointikieli Java.

## Harjoitustyön aihe

Harjoitustyössä toteutan Dijkstran- ja A*-reitinhakualgoritmit, joiden avulla on mahdollista selvittää verkossa oleva kahden solmun välinen lyhin polku. 
Algoritmeihin liittyvät tietorakenteet, kuten prioriteettijono, toteutan myös itse.

Reitinhaun pohjina aion käyttää [MovingAi](https://movingai.com/benchmarks/grids.html)n sivuilta löytyviä karttoja. Suunnitelmana on graafisen käyttöliittymän kautta 
valita alku- ja päätepiste, selvittää reitti valitulla algoritmilla, piirtää se kartalle ja ilmoittaa kulunut aika käyttöliittymässä.

## Tavoitteet

Tavoitteena on tutkia algoritmien toimintaa erimuotoisilla kartoilla sekä vertailla niiden toteutuneita suoritusaikoja ja muistinkäyttöä.

### Dijkstran algoritmi

Aikavaativuus _O_((E+V)log(V)).

Tilavaativuus _O_(V).

### A*-algoritmi

Huonolla heurestiikalla A* toimii kuten Dijkstran algoritmi joten aikavaativuus huonossa tapauksessa on ainakin _O_((E+V)log(V)).

Tilavaativuudeksi pitäisi tulla _O_(V).

Vaativuudet riippuvat oleellisesti tietorakenteiden toteutuksesta sekä A*-algoritmille valitusta heurestiikasta, joten ne tulevat varmasti muuttumaan. 
Todennäköinen vaihtoehto on käyttää euklidista etäisyyttä A*:ssa.

_(V = solmujen lukumäärä, E = kaarien lukumäärä)_

## Laajentaminen

Mahdollisesti toteutan myös Jump Point Search algoritmin Dijkstran ja A* rinnalle vertailtavaksi jos aikaa on.
