package suho.pofol.mappers.chat;

import org.apache.ibatis.annotations.Mapper;
import suho.pofol.dto.chat.ChatListDTO;

import java.util.List;

@Mapper
public interface ChatMapper {

    List<ChatListDTO> FollowerSelect(int userId);
}
