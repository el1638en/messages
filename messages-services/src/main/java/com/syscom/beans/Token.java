package com.syscom.beans;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString(exclude = {})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {})
@Entity
@Table(name = "T_TOKEN")
public class Token extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TOKEN_SEQUENCE_GENERATOR", sequenceName = "TOKEN_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TOKEN_SEQUENCE_GENERATOR")
	@Column(name = "T_ID")
	private Long id;

	@Column(name = "T_VALUE", length = 500, nullable = false)
	private String value;

	@Column(name = "T_EXPIRATION_DATE", nullable = false)
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	private LocalDateTime dateExpiration;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "T_USER_ID")
	@NotFound(action = NotFoundAction.IGNORE)
	private User user;

}
