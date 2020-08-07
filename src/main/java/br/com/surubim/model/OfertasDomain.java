package br.com.surubim.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OfertasDomain implements Serializable {

	private static final long serialVersionUID = -7759336206012025424L;

	public OfertasDomain() {
	}

	public OfertasDomain(Long id, String nome, double valor) {
		this.id = id;
		this.name = nome;
		this.valor = valor;
	}

	public OfertasDomain(String nome, double valor) {
		this.name = nome;
		this.valor = valor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OfertasDomain other = (OfertasDomain) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private double valor;

}
