package com.rwy.spider.bean.quartz;

// Generated 2014-10-28 8:26:25 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * QrtzFiredTriggersId generated by hbm2java
 */
@Embeddable
public class QrtzFiredTriggersId implements java.io.Serializable {

	private String schedName;
	private String entryId;

	public QrtzFiredTriggersId() {
	}

	public QrtzFiredTriggersId(String schedName, String entryId) {
		this.schedName = schedName;
		this.entryId = entryId;
	}

	@Column(name = "SCHED_NAME", nullable = false, length = 120)
	public String getSchedName() {
		return this.schedName;
	}

	public void setSchedName(String schedName) {
		this.schedName = schedName;
	}

	@Column(name = "ENTRY_ID", nullable = false, length = 95)
	public String getEntryId() {
		return this.entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof QrtzFiredTriggersId))
			return false;
		QrtzFiredTriggersId castOther = (QrtzFiredTriggersId) other;

		return ((this.getSchedName() == castOther.getSchedName()) || (this
				.getSchedName() != null && castOther.getSchedName() != null && this
				.getSchedName().equals(castOther.getSchedName())))
				&& ((this.getEntryId() == castOther.getEntryId()) || (this
						.getEntryId() != null && castOther.getEntryId() != null && this
						.getEntryId().equals(castOther.getEntryId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getSchedName() == null ? 0 : this.getSchedName().hashCode());
		result = 37 * result
				+ (getEntryId() == null ? 0 : this.getEntryId().hashCode());
		return result;
	}

}
