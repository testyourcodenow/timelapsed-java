package com.github.testyourcodenow;

public enum Notation {
	TWITTER("twitter"), SHORT("short"), LONG("long");

	private String notation;

	private Notation(String notation) {
		this.notation = notation;
	}

	@Override
	public String toString() {
		return this.notation;
	}
}
