# Toteutusdokumentti

## Algoritmien aika- ja tilavaativuudet

### Prioriteettijono
| operaatio              | Aikavaativuus     |
| ---------------------- |:-----------------:|
| Alkion lisääminen      | _O_(log n)        |
| Pienimmän alkion haku ja poisto | _O_(log n)        |


### A*
| tapaus                   | Aikavaativuus      |
| ------------------------ |:------------------:|
| Huonoimmassa tapauksessa | _O_((E + V) log V) |
| Keskimäärin              | ?                  |

### JPS
| tapaus                   | Aikavaativuus      |
| ------------------------ |:------------------:|
| Huonoimmassa tapauksessa | _O_((E + V) log V) |
| Keskimäärin              | ?                  |

## Suorituskykyvertailu

Tarkemmin suorituskykyvertailusta [testausdokumentissa.](https://github.com/hoffrenm/reitinhaku/blob/master/dokumentaatio/testausdokumentti.md#testausasetelma)
