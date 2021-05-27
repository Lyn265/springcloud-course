package com.lyn.server.mapper.my;

import com.lyn.server.dto.SortDto;
import org.apache.ibatis.annotations.Param;

public interface MyCourseMapper {

    int updateTime(@Param("courseId") String courseId);

    void updateSort(@Param("sortDto") SortDto sortDto);

    void moveSortForward(@Param("sortDto") SortDto sortDto);

    void moveSortBackward(@Param("sortDto") SortDto sortDto);
}
