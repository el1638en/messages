package com.syscom.beans;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString(exclude = { "fonctions" })
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = { "fonctions" })
@Entity
@Table(name = "T_ROLE")
public class Role extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ROLE_SEQUENCE_GENERATOR", sequenceName = "ROLE_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_SEQUENCE_GENERATOR")
	@Column(name = "R_ID")
	private Long id;

	@Column(name = "R_CODE", nullable = false)
	private String code;

	@Column(name = "R_LIBELLE", nullable = false)
	private String libelle;

	@ManyToMany(mappedBy = "roles")
	private List<Fonction> fonctions;

}