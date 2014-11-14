package com.rwy.spider.bean.quartz;

// Generated 2014-10-28 8:26:25 by Hibernate Tools 4.0.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * QrtzSimpleTriggers generated by hbm2java
 */
@Entity
@Table(name = "qrtz_simple_triggers", catalog = "spider")
public class QrtzSimpleTriggers implements java.io.Serializable {

	private QrtzSimpleTriggersId id;
	private QrtzTriggers qrtzTriggers;
	private long repeatCount;
	private long repeatInterval;
	private long timesTriggered;

	public QrtzSimpleTriggers() {
	}

	public QrtzSimpleTriggers(QrtzTriggers qrtzTriggers, long repeatCount,
			long repeatInterval, long timesTriggered) {
		this.qrtzTriggers = qrtzTriggers;
		this.repeatCount = repeatCount;
		this.repeatInterval = repeatInterval;
		this.timesTriggered = timesTriggered;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "schedName", column = @Column(name = "SCHED_NAME", nullable = false, length = 120)),
			@AttributeOverride(name = "triggerName", column = @Column(name = "TRIGGER_NAME", nullable = false, length = 200)),
			@AttributeOverride(name = "triggerGroup", column = @Column(name = "TRIGGER_GROUP", nullable = false, length = 200)) })
	public QrtzSimpleTriggersId getId() {
		return this.id;
	}

	public void setId(QrtzSimpleTriggersId id) {
		this.id = id;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public QrtzTriggers getQrtzTriggers() {
		return this.qrtzTriggers;
	}

	public void setQrtzTriggers(QrtzTriggers qrtzTriggers) {
		this.qrtzTriggers = qrtzTriggers;
	}

	@Column(name = "REPEAT_COUNT", nullable = false)
	public long getRepeatCount() {
		return this.repeatCount;
	}

	public void setRepeatCount(long repeatCount) {
		this.repeatCount = repeatCount;
	}

	@Column(name = "REPEAT_INTERVAL", nullable = false)
	public long getRepeatInterval() {
		return this.repeatInterval;
	}

	public void setRepeatInterval(long repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	@Column(name = "TIMES_TRIGGERED", nullable = false)
	public long getTimesTriggered() {
		return this.timesTriggered;
	}

	public void setTimesTriggered(long timesTriggered) {
		this.timesTriggered = timesTriggered;
	}

}