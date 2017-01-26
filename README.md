# Job sites aggregator
-----
Job sites parser, which only goal is to simplify finding process.

Supported Job sites:

* [HH](https://hh.ru)
* [Moikrug](https://moikrug.ru)

-----
Aggregator.java contains main method.
All you hava to do is to edit config.properties with own city and vacancy.
Search result will come in sight in vacancies.html

![alt tag](https://raw.github.com/TuvaevAndrey/job-sites-parser/master/src/main/resources/vacancies.png) 



-----

To provide other sites you only have to implement Strategy.java and change provider in Aggregator.java.
 
