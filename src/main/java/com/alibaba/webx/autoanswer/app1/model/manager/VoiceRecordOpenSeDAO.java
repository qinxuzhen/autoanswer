package com.alibaba.webx.autoanswer.app1.model.manager;

import java.util.List;

import com.alibaba.webx.autoanswer.app1.model.RecordDO;

/**VoiceRecord��RDS�����ӿ�
 * @author wangjiawei.wjw
 * 2016-08-28
 */
public interface VoiceRecordOpenSeDAO {
	public List<RecordDO> queryModelIdByText(String voiceTextFrag);
}
