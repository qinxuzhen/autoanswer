package com.alibaba.webx.autoanswer.app1.model.manager;

import java.util.List;

/**VoiceRecord的RDS操作接口
 * @author wangjiawei.wjw
 * 2016-08-28
 */
public interface VoiceRecordOpenSeDAO {
	public List<String> queryModelIdByText(String voiceTextFrag);
}
