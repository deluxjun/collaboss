package com.inzent.sbp.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

	@CreatedDate
	@NotNull
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@LastModifiedDate
	@NotNull
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;
}