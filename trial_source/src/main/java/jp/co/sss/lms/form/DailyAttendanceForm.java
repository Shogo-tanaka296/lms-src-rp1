
package jp.co.sss.lms.form;


import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 日次の勤怠フォーム
 * 
 * @author 東京ITスクール
 */
@Data
public class DailyAttendanceForm {

	/** 受講生勤怠ID */
	private Integer studentAttendanceId;
	/** 途中退校日 */
	private String leaveDate;
	/** 日付 */
	private String trainingDate;
	/** 出勤時間 */
	private String trainingStartTime;
	/**　出勤時間(時)  */
	private Integer trainingStartTimeHour;
	/**　出勤時間(分) */
	private Integer trainingStartTimeMinute;
	/** 退勤時間 */
	private String trainingEndTime;
	/** 退勤時間(時)  */
	private Integer trainingEndTimeHour;
	/** 退勤時間(分)  */
	private Integer trainingEndTimeMinute;
	/** 中抜け時間 */
	private Integer blankTime;
	/** 中抜け時間（画面表示用） */
	private String blankTimeValue;
	/** ステータス */
	private String status;
	/** 備考 */
	@Size(max = 100, message = "{maxlength}")
	private String note;
	/** セクション名 */
	private String sectionName;
	/** 当日フラグ */
	private Boolean isToday;
	/** エラーフラグ */
	private Boolean isError;
	/** 日付（画面表示用） */
	private String dispTrainingDate;
	/** ステータス（画面表示用） */
	private String statusDispName;
	/** LMSユーザーID */
	private String lmsUserId;
	/** ユーザー名 */
	private String userName;
	/** コース名 */
	private String courseName;
	/** インデックス */
	private String index;
	
	/**
	 * 9月29追加
	 * 退勤時間より出勤時間が早い場合カット
	 * @author 田中
	 * @return
	 */

	@AssertTrue(message = "{attendance.trainingTimeRange}")
	public boolean isTrainingTimeRange() { 
		//個別のnullチェックは他メソッドに任せて今回はスルー
		boolean startTime = trainingStartTime == null || trainingStartTime == null; 
		boolean endTime   = trainingEndTime == null || trainingEndTime == null;
		if(startTime || endTime) {
			return true;
		}
		
		//分に直して比べる
		Integer startMin = (trainingStartTimeHour * 60) + trainingStartTimeMinute;
		Integer endMin= (trainingEndTimeHour *60) + trainingEndTimeMinute;

		//出勤時間(startMin) < 退勤時間(endMin)ならスルー
		return startMin < endMin ;
	}
	
	/**9月29日
	 * 出勤時間が空で退勤時間が選択されているときのみカット
	 * @author 田中
	 * @return
	*/
	@AssertTrue(message ="{attendance.punchInEmpty}")
	public boolean isPunchInEmpty() {
		//個別のnullチェックは他メソッドに任せて今回はスルー
		boolean startTime = trainingStartTime == null || trainingStartTime == null; 
		boolean endTime   = trainingEndTime == null || trainingEndTime == null;
		if(startTime || endTime) {
			return true;
		}
		
		boolean isStartEmpty = 
				trainingStartTimeHour != null && trainingStartTimeMinute != null;
		boolean isEndEmpty   =
				trainingEndTimeHour != null && trainingEndTimeMinute != null;
		if(isStartEmpty && !isEndEmpty) {
			return false;
		}
		return true;
	}
	
	/**9月29日追加
	 * 出勤時間の時間と分が揃っているか、どちらも空白のみカット
	 * @author 田中
	 * @return
	 */
	@AssertTrue(message ="{input.invalid}")
	public boolean isEmptyStartTime() {
		boolean startHour = trainingStartTimeHour != null;
		boolean startMinute =trainingStartTimeHour != null;
		return (startHour && startMinute) || (!startHour && !startMinute);
	}
	/**9月29日追加
	 * 退勤時間の時間と分が揃っているか、どちらも空白のみカット
	 * @author 田中
	 * @return
	 */
	@AssertTrue(message ="{input.invalid}")
	public boolean isEmptyEndTime() {
		boolean endHour = trainingEndTimeHour != null;
		boolean endMinute = trainingEndTimeHour != null;
		return (endHour && endMinute) || (!endHour && !endMinute);
	}
}
