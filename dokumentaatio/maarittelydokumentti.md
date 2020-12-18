# Määrittelydokumentti

Opinto-ohjelma tietojenkäsittelytieteen kanditaatti (TKT).

Dokumentaation kieli on suomi ja ohjelmointikieli Java.

## Harjoitustyön aihe

Harjoitustyössä toteutan A*- ja Jump point search -algoritmit, joiden avulla on mahdollista selvittää ruudukossa oleva kahden pisteen välinen lyhin polku. 
Algoritmeihin liittyvät tietorakenteet, kuten prioriteettijono, toteutan myös itse.

Reitinhaun pohjina aion käyttää [MovingAi](https://movingai.com/benchmarks/grids.html)n sivuilta löytyviä karttoja. Suunnitelmana on graafisen käyttöliittymän kautta 
valita alku- ja päätepiste, selvittää reitti valitulla algoritmilla, piirtää se kartalle ja ilmoittaa kulunut aika käyttöliittymässä.

## Tavoitteet

Tavoitteena on tutkia algoritmien toimintaa erimuotoisilla kartoilla sekä vertailla niiden toteutuneita suoritusaikoja.

### A*-algoritmi

Huonolla heurestiikalla A* toimii kuten Dijkstran algoritmi joten aikavaativuus huonossa tapauksessa on ainakin _O_((E+V)log(V)).

Tilavaativuudeksi pitäisi tulla _O_(V).

Useimmissa tapauksissa heuristiikka kuitenkin parantaa algoritmin toimintaa merkittävästi.

### Jump Point Search

Aikavaativuus _O_((E+V)log(V)).

Tilavaativuus _O_(V).

Vaikka kyseessä onkin pohjimmiltaan A*-algoritmin optimointi, niin JPS karsii tarkasteltavia solmuja huomattavasti karsintasääntöjen mukaisesti ja siten sen pitäisi toimia huomattavasti A* nopeammin.

### Prioriteettijono (minimikeko)

Alkion lisääminen _O_(log n)

Pienimmän alkion haku ja poisto _O_(log n)

Vaativuudet riippuvat oleellisesti tietorakenteiden toteutuksesta sekä algoritmeille valitusta heurestiikasta, joten ne tulevat varmasti muuttumaan. 

_(V = solmujen lukumäärä, E = kaarien lukumäärä)_

## Laajentaminen

Erilaisia heuristiikkoja, joiden vaikutusta algoritmien toimintaan voi vertailla.
