# "Komponentenbasierte Programmierung"

## Aufgabenstellung
Die detaillierte [Aufgabenstellung](TASK.md) beschreibt die notwendigen Schritte zur Realisierung.

## Notes
`persistence.xml`
ORM: Hibernate  
Annotations:
* `@Entity`
* `@Id`
* `@ManyToOne`
* `@ManyToMany`
* `@OneToMany`
* `@Email`
* `@Column`
* `@Transient`

Inline Queries OO Variante

Wenn `@Entity` gibt werden alle Atribute in der Datenbank gespeichert.
Wenn dies nicht so sein soll muss man `@Transient`

## Implementierung

Als ertstes die Astha Datei geöffnet und alle Dateien in model expotiert.  
Man **muss** in der `persistence.xml` `<class>model.Bahnhof</class>` hinzufgen damit
Hibernate das Model erkennt.

## Quellen
* [LEFT JOIN](https://www.objectdb.com/java/jpa/query/jpql/from#LEFT_OUTER_JOIN_)