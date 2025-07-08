package org.example.assignment01.dto.reponse;

import lombok.Data;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Lớp `ApiResponse<T>` dùng để wrap kết quả trả về từ các API theo một định dạng chuẩn.
 *
 * Cấu trúc gồm các thành phần chính:
 * - `status`: Trạng thái phản hồi, ví dụ: "SUCCESS", "ERROR"
 * - `message`: Thông báo mô tả kết quả
 * - `data`: Dữ liệu trả về (nếu thành công)
 * - `errors`: Thông tin lỗi chi tiết (nếu có lỗi xảy ra)
 * - `timestamp`: Thời gian phản hồi (tự động gán tại thời điểm tạo đối tượng)
 *
 * Sử dụng annotation `@JsonInclude(JsonInclude.Include.NON_NULL)` để bỏ qua các trường null khi serialize sang JSON.
 *
 * @param <T> Kiểu dữ liệu của phần `data` trả về (ví dụ: UserResponse, List<DonationResponse>, ...)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private String status; // SUCCESS hoặc ERROR
    private String message; // Mô tả kết quả xử lý
    private T data;         // Dữ liệu thành công (nếu có)
    private Object errors;  // Lỗi chi tiết (nếu có)
    private final LocalDateTime timestamp = LocalDateTime.now(); // Tự động gán thời điểm phản hồi

    /**
     * Phương thức static để tạo response thành công có kèm dữ liệu.
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>("SUCCESS", message, data, null);
    }

    /**
     * Phương thức static để tạo response thành công không kèm dữ liệu.
     */
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>("SUCCESS", message, null, null);
    }

    /**
     * Phương thức static để tạo response thất bại kèm lỗi chi tiết.
     */
    public static <T> ApiResponse<T> error(String message, Object errors) {
        return new ApiResponse<>("ERROR", message, null, errors);
    }

    /**
     * Phương thức static để tạo response thất bại không kèm lỗi chi tiết.
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>("ERROR", message, null, null);
    }
}
