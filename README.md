
# Ứng dụng Quản lý kho hàng


## Tổng quan

Ứng dụng được xây dựng dựa trên mô hình Client - Server với 2 tiến trình, tiến trình ở máy chủ và các tiến trình ở máy khách giao tiếp với nhau bằng cách gửi và nhận các thông điệp qua Socket.


Server sau khi khởi động sẽ chờ các kết nối và lắng nghe các yêu cầu từ phía Client. Khi có yêu cầu từ Client, Server đọc và xử lý yêu cầu, sau đó gửi quảng bá (Broadcast) phản hồi tới tất cả các Client đang kết nối, các Client nhận phản hổi, xử lý và cập nhật dữ liệu của mình

Số hiệu cổng (port): 3000

Định dạng thông điệp: `Method/Parameters`
 - `Method` : Tên phương thức thao tác với dữ liệu
 - `Parameters` : Danh sách các tham số, ngăn cách nhau bởi dấu /

#### Thông điệp yêu cầu từ Client 

Tiến trình Client khi khởi động sẽ mặc định gửi yêu cầu lấy dữ liệu kho hàng và dữ liệu tài khoản từ Server và cập nhật dữ liệu cho tiến trình của mình

- Thông điệp yêu cầu lấy dữ liệu kho hàng
```bash
  Get/
```
- Thông điệp yêu cầu lấy dữ liệu tài khoản
```bash
  Get-User/
```
- Thông điệp yêu cầu thêm mới sản phẩm
```bash
  Create/data
```
| Tham số | Kiểu     | Mô tả                     |
| :-------- | :------- | :-------------------------------- |
| `data`      | `xâu xml` | **Bắt buộc**. Dữ liệu của sản phẩm thêm mới|

- Thông điệp yêu cầu chỉnh sửa sản phẩm
```bash
  Update/id/product-data
```
| Tham số | Kiểu     | Mô tả                      |
| :-------- | :------- | :-------------------------------- |
| `id`      | `xâu` | **Bắt buộc**. Mã số sản phẩm cần sửa |
| `data`      | `xâu xml` | **Bắt buộc**. Dữ liệu mới của sản phẩm cần sửa |

- Thông điệp yêu cầu xóa sản phẩm
```bash
  Delete/id
```
| Tham số | Kiểu     | Mô tả                      |
| :-------- | :------- | :-------------------------------- |
| `id`      | `xâu` | **Bắt buộc**. Mã số sản phẩm cần xóa |

#### Thông điệp phản hồi của server (Response)

```bash
  Reset/data
```
## Chức năng

- Thêm, sửa, xóa, xem chi tiết sản phẩm
- Sắp xếp và tìm kiếm theo thuộc tính của sản phẩm
- Thống kê, biểu diễn trực quan dữ liệu kho hàng qua biểu đồ
- Đăng ký, đăng nhập, đăng xuất và quản lý tài khoản trong hệ thống
## Cài đặt


Download Server:
```bash
  git clone https://github.com/nguyennam-github/QLKH-Server.git
```
Download Client:
```bash
  git clone https://github.com/nguyennam-github/QLKH-Client.git
```