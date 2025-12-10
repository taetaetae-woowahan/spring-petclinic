package org.springframework.samples.petclinic.owner;

public class CursorPageRequest {

	private Integer cursor;
	private int size = 10;

	public CursorPageRequest() {
	}

	public CursorPageRequest(Integer cursor, int size) {
		this.cursor = cursor;
		this.size = size;
	}

	public Integer getCursor() {
		return cursor;
	}

	public void setCursor(Integer cursor) {
		this.cursor = cursor;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
