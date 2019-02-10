package com.syscom.beans;

import static java.time.LocalDateTime.now;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;
import lombok.ToString;

/**
 * Entite super-classe des objets ORM Mapping Objet - Relationnel
 *
 */
@MappedSuperclass
@Data
@ToString(exclude = {})
public class BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "CREATE_DATE")
	protected LocalDateTime createDate;

	@Column(name = "UPDATE_DATE")
	protected LocalDateTime updateDate;

	/**
	 * Avant un persist en BDD, mise à jour des dates techniques s'ils étaient
	 * nulles
	 *
	 */
	@PrePersist
	void prePersist() {
		this.createDate = (this.createDate == null ? now() : this.createDate);
		this.updateDate = (this.updateDate == null ? now() : this.updateDate);
	}

	/**
	 * Mise à jour de la date de modification avant une opération de modification
	 */
	@PreUpdate
	void preUpdate() {
		this.updateDate = now();
	}

}
