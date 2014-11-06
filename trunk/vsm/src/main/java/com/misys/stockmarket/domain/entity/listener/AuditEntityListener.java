package com.misys.stockmarket.domain.entity.listener;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.misys.stockmarket.domain.entity.AuditableEntity;

public class AuditEntityListener {

	private static final Log LOG = LogFactory.getLog(AuditEntityListener.class);

	@PrePersist
	public void prePersist(AuditableEntity e) {
		e.setCreatedDate(new Date());

	}

	@PreUpdate
	public void preUpdate(AuditableEntity e) {
		e.setLastModifiedDate(new Date());
	}
}
