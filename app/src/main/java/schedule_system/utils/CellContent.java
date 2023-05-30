package schedule_system.utils;

/**
 * 发送至前端的日程表单元格内容
 */
public record CellContent(
        String name,
        String[] students,
        String location,
        boolean isActivity) {
}
