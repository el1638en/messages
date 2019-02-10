package com.syscom.beans;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@ToString(exclude = {})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {})
@Entity
@Table(name = "T_MESSAGE")
public class Message extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "MESSAGE_SEQ_GENERATOR", sequenceName = "MESSAGE_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGE_SEQ_GENERATOR")
	@Column(name = "M_ID")
	private Long id;

	@Column(name = "M_TITLE", nullable = false)
	private String title;

	@Column(name = "M_CONTENT", nullable = false)
	private String content;

	@Column(name = "M_BEGIN_DATE", nullable = false)
	private LocalDate beginDate;

	@Column(name = "M_END_DATE", nullable = false)
	private LocalDate endDate;

}
