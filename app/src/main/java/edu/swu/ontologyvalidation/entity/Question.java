package edu.swu.ontologyvalidation.entity;

/**
 * Created by Tuister on 2016/8/14.
 */
public class Question {
    private int id;
    private String text_zh;
    private String text_en;
	private String answer;
    private int matched;
	private boolean checked;


    public static final int TRUE = 1;
    public static final int FALSE = 0;
    public static final int NOT_SURE =2;

	public static final int CHECKED=1;
    public static final int NOT_CHECKED = 0;


	public Question(){

	}

	public Question(int id, String text_zh, String text_en, boolean checked, int matched, String answer) {
		this.id = id;
		this.text_zh = text_zh;
		this.text_en = text_en;
		this.checked = checked;
		this.matched = matched;
		this.answer = answer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText_zh() {
		return text_zh;
	}

	public void setText_zh(String text_zh) {
		this.text_zh = text_zh;
	}

	public String getText_en() {
		return text_en;
	}

	public void setText_en(String text_en) {
		this.text_en = text_en;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public int getMatched() {
		return matched;
	}

	public void setMatched(int matched) {
		this.matched = matched;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "Question{" +
				"id=" + id +
				", text_zh='" + text_zh + '\'' +
				", text_en='" + text_en + '\'' +
				", checked=" + checked +
				", matched=" + matched +
				", answer='" + answer + '\'' +
				'}';
	}
}
