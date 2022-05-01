package dev.leandro.erllet.stub;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Data
public class StubEntity {

    @Id
    private Integer id;

}
