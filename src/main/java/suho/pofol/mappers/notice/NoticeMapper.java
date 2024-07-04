package suho.pofol.mappers.notice;

import org.apache.ibatis.annotations.Mapper;
import suho.pofol.domain.notice.Notice;
import suho.pofol.dto.notice.NoticeDTO;

import java.util.List;

@Mapper
public interface NoticeMapper {
    List<NoticeDTO> findNoticesWithFollowerName(int userId);
}