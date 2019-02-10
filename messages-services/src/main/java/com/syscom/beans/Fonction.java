package com.syscom.beans;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString(exclude = { "roles" })
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = { "roles" })
@Entity
@Table(name = "T_FONCTION")
public class Fonction extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "FONCTION_SEQUENCE_GENERATOR", sequenceName = "FONCTION_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FONCTION_SEQUENCE_GENERATOR")
	@Column(name = "F_ID")
	private Long id;

	@Column(name = "F_CODE", nullable = false)
	private String code;

	@Column(name = "F_LIBELLE", nullable = false)
	private String libelle;

	@ManyToMany
	@JoinTable(name = "T_ROLE_FONCTION", joinColumns = @JoinColumn(name = "FONCTION_ID", referencedColumnName = "F_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "R_ID"))
	private List<Role> roles;

}
