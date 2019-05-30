# "Komponentenbasierte Programmierung"

## Aufgabenstellung
Die detaillierte [Aufgabenstellung](TASK.md) beschreibt die notwendigen Schritte zur Realisierung.

## Notes
`persistence.xml`
ORM: Hibernate
Annotations:

| Annotation  | Description                                            |
| ----------- | ------------------------------------------------------ |
| @Entity     | Marks an class as an persistable object                |
| @Id         | Marks the attribute as the (primary) Id of the object  |
| @ManyToOne  | Marks the attribute to be an many to one relationship  |
| @ManyToMany | Marks the attribute to ba an many to many relationship |
| @OneToMany  | Marks the attribute to be an one to many relationship  |
| @Email      | Marks the attribute to be validated as an email        |
| @Column     | Sets specific arguments for the column in the database |
| @Transient  | Marks the attribute to not be saved in the database    |

Inline Queries OO Variante

Wenn `@Entity` angegeben ist werden alle Attribute in der Datenbank gespeichert.
Wenn dies nicht so sein soll muss man `@Transient` bei den Attributen hinzufügen die nicht gespeichert werden sollen.

`@PersistenceUnit(unitName = "westbahn")` bei `EntityManagerFactory `

## Implementierung

Als ertstes die Astha Datei geöffnet und alle Dateien in model expotiert.

Man **muss** in der `persistence.xml` innerhalb der `<persistence-unit>`  für alle Klassen die man Persistieren will einen Eintrag in der Form `<class>model.Bahnhof</class>` hinzufügen. Wobei `model.Bahnhof` durch die Klasse zu ersetzten ist die man Persistieren will.

### Task 1

Danach habe ich in den Klassen getter und setter generiert, damit JPA auf die Attribute zugreifen kann.

Weiters habe ich die Attribute der Klassen mit den Notwendigen Annotations ausgestattet (siehe oben).

Dann habe ich die Klasse `Reservierung` mittels `XML-mapping` konfiguriert.
Dabei habe ich eine Datei `orm.xml` (im META-INF ordner) erstellt. Diese wird automatisch von hibernate gelesen und verwendet. 

```xml
<hibernate-mapping>
    <class name="model.Reservierung">

        <id name="ID" column="id">
             <generator class="native" />
        </id>

        <property name="datum" type="timestamp"/>
        <property name="praemienMeilenBonus" />
        <property name="preis" />
        <property name="status" />

        <many-to-one name="zug" class="model.Zug" />
        <many-to-one name="strecke" class="model.Strecke" />
        <many-to-one name="benutzer" class="model.Benutzer">
                <column name="benutzer" />
        </many-to-one>

    </class>
</hibernate-mapping>
```

In der Datei definieren wir eine Klasse mit dem namen `model.Reservierung`. Dies ist unsere Java Klasse. Weiters definieren wir eine ID und konfigurieren ein paar Properties. Zum Schluss habe ich noch die Relationen eingerichtet.

### Task 2

Dann habe ich die erste Named-Query eingerichtet.

```java
@NamedQuery(
		name="Benutzer.getReservierungenByEmail",
		query = "SELECT reservierungen FROM Benutzer WHERE eMail=:email"
)
```

Diese findet alle Reservierungen von einem Benutzer, wobei dieser Benutzer mit der email-Adresse identifiziert wird.

Die nächste Query findet alle Benutzer mit einer Monatskarte. Dabei sucht sie nach Tickets wo der typ 1 ist. Es ist deswegen 1 weil im Enum `ZeitkartenTyp` die Monatskarte der Zweite Eintrag ist. (Java fängt bei 0 an zu zählen: 2 => 1).

```java
@NamedQuery(
		name = "Benutzer.getAllWithMonatskarte",
		query = "SELECT b FROM Benutzer b LEFT JOIN b.tickets t WHERE t.typ=1" // ZeitkartenTyp.MONATSKARTE = 1
)
```

 Die Named-queries kann man Aufrufen indem man über den `EntityManager` eine NamedQuery erstellt und den Namen angibt. Danach kann man die Parameter setzten die diese NamedQuery besitzt und dann Ausführen.

```java
public static void task02a() throws ParseException {
    List<Reservierung> res = entitymanager.createNamedQuery("Benutzer.getReservierungenByEmail")
        .setParameter("email", "david@langheiter.com")
        .getResultList();

    System.out.println("\nRESERVIERUNGEN");
    System.out.println("-------------------------------");
    for (Reservierung r : res) {
        System.out.println(r);
    }
    System.out.println("-------------------------------");
}
```

#### Task 2 c

Diese Aufgabenstellung ist mit der vorgegebenen Struktur nicht eindeutig durchführbar, da ein Ticket keinen expliziten Zusammenhang zu einem Ticket hat. Es gibt ein paar "Lösungen" die aber nicht zum beschriebenen Ergebnis führen.

Man könnte schauen ob auf einer Strecke eine Reservierung ist, oder nicht und je nachdem das Ticket bekommen. Das Problem daran ist, dass wenn es auch nur eine Reservierung auf der Strecke gibt alle Tickets für diese Strecke als "haben eine Reservierung" markiert werden und somit nicht zurück geliefert werden.

Eine andere Möglichkeit ist es über den Benutzer zu machen, nur da kann man auch nicht eindeutig einem Ticket einer Reservierung zuordnen. Weil sobald ein Benutzer eine Reservierung für eine Strecke hat, aber zum Beispiel zwei Tickets für diese Strecke, werden wieder beide als "haben eine Reservierung" markiert. Und da ein Ticket kein Datum hat, können wir auch das nicht verwenden.

## Quellen

* [@Email](<http://docs.jboss.org/hibernate/stable/validator/api/index.html?org/hibernate/validator/constraints/Email.html>)
* [LEFT JOIN](https://www.objectdb.com/java/jpa/query/jpql/from#LEFT_OUTER_JOIN_)