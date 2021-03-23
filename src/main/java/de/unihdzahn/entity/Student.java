/**
 * Representation of a student in the DB.
 *
 * @author Julian B&uuml;cher
 */

package de.unihdzahn.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@JsonPropertyOrder(
        {"hs_id", "matrikelnummer", "prename", "surname", "gender", "age"}
)
@Entity(name = "Student")
@Table(name = "student", schema = "dbo")
@JsonIgnoreProperties(ignoreUnknown = false)
@Proxy(lazy = false)
public class Student implements Serializable {

    @Id
    @GeneratedValue(generator = "genUuid")
    @GenericGenerator(name = "genUuid", strategy = "uuid2")
    @Type(type = "pg-uuid")
    @Column(name = "hs_id", nullable = false, unique = true)
    @Getter
    private UUID hs_id;

    @Column(name = "matrikelnummer", nullable = false, unique = false)
    @Getter
    private String matrikelnummer;

    @Column(name = "prename", nullable = false, unique = false)
    @Getter
    private String prename;

    @Column(name = "surname", nullable = false, unique = false)
    @Getter
    private String surname;

    @Column(name = "gender", nullable = false, unique = false)
    @Getter
    private String gender;

    @Column(name = "age", nullable = false, unique = false)
    @Getter
    private int age;

    public Student(@NotNull UUID hs_id, @NotNull String matrikelnummer,
                   @NotNull String prename, @NotNull String surname,
                   @NotNull String gender, @NotNull int age) {
        this.hs_id = hs_id;
        this.matrikelnummer = matrikelnummer;
        this.prename = prename;
        this.surname = surname;
        this.gender = gender;
        this.age = age;
    }

    public Student() {

    }

    @Override
    public String toString() {
        return String.format(" matrikelnummer: %s, prename: %s, surname: %s",
                String.valueOf(this.matrikelnummer),
                this.prename.toString(), this.surname.toString());
    }

    @Override
    public boolean equals(Object object) {
        if (object == this || !(object instanceof Student)) {
            return false;
        }

        Student student = (Student) object;

        return this.matrikelnummer == student.getMatrikelnummer();
    }
}
