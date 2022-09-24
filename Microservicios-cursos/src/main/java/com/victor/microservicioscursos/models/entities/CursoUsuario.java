package com.victor.microservicioscursos.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "crusos_usuarios")
public class CursoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "usuario_id", unique = true)
    private Integer usuarioId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof CursoUsuario)) {
            return false;
        }
        CursoUsuario o = (CursoUsuario) obj;
        return this.usuarioId != null && this.usuarioId.equals(o.usuarioId);
    }
}
