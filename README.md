
# Ứng dụng Quản lý kho hàng

A brief description of what this project does and who it's for


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

- Thông điệp yêu cầu tạo tài khoản mới
```bash
  Create-User/data
```
| Tham số | Kiểu     | Mô tả                     |
| :-------- | :------- | :-------------------------------- |
| `data`      | `xâu xml` | **Bắt buộc**. Dữ liệu của tài khoản tạo mới|

- Thông điệp yêu cầu chỉnh sửa sản phẩm
```bash
  Update/id/data
```
| Tham số | Kiểu     | Mô tả                      |
| :-------- | :------- | :-------------------------------- |
| `id`      | `xâu` | **Bắt buộc**. Mã số sản phẩm cần sửa |
| `data`      | `xâu xml` | **Bắt buộc**. Dữ liệu mới của sản phẩm cần sửa |

- Thông điệp yêu cầu chỉnh sửa tài khoản
```bash
  Update/id/data
```
| Tham số | Kiểu     | Mô tả                      |
| :-------- | :------- | :-------------------------------- |
| `username`      | `xâu` | **Bắt buộc**. Tên tài khoản cần sửa |
| `data`      | `xâu xml` | **Bắt buộc**. Dữ liệu mới của tài khoản cần sửa |

- Thông điệp yêu cầu xóa sản phẩm
```bash
  Delete/id
```
| Tham số | Kiểu     | Mô tả                      |
| :-------- | :------- | :-------------------------------- |
| `id`      | `xâu` | **Bắt buộc**. Mã số sản phẩm cần xóa |

- Thông điệp yêu cầu xóa tài khoản
```bash
  Delete/id
```
| Tham số | Kiểu     | Mô tả                      |
| :-------- | :------- | :-------------------------------- |
| `username`      | `xâu` | **Bắt buộc**. Tên tài khoản cần xóa |

#### Thông điệp phản hồi của server (Response)

- Thông điệp phản hồi các yêu cầu Get/, Create/, Update/, Delete/
từ Client

```bash
  Reset/data
```

| Tham số | Kiểu     | Mô tả                      |
| :-------- | :------- | :-------------------------------- |
| `data`      | `xâu xml` | Dữ liệu toàn bộ sản phẩm sau khi thực hiện các thao tác với dữ liệu |

## Chức năng
#### Đăng ký, đăng nhập, đăng xuất và quản lý tài khoản trong hệ thống

- Đăng nhập

- Thêm, sửa, xóa, xem chi tiết sản phẩm
- Sắp xếp và tìm kiếm theo thuộc tính của sản phẩm
- Thống kê, biểu diễn trực quan dữ liệu kho hàng qua biểu đồ


### Đăng nhập
 - Tại màn hình đăng nhâp người dùng nhập tên tài khoản và mật khẩu để đăng nhập vào hệ thống

![App Screenshot](https://i.ibb.co/BNcfJ0P/Screenshot-2024-04-19-135406.png)

- Trong khi người dùng nhập tên tài khoản, ứng dụng sẽ tự động kiểm tra tên tài khoản đã tồn tại chưa và hiển thị thông báo cho đến khi người dùng nhập một tên tài khoản hợp lệ

![App Screenshot](https://i.ibb.co/3s2nFjZ/Screenshot-2024-04-19-135241.png)

![App Screenshot](https://i.ibb.co/sRQDXY4/Screenshot-2024-04-19-134714.png)

- Sau khi nhập một tên tài khoản hợp lệ, người dùng tiếp tục nhập mật khẩu cho tài khoản này và click vào nút đăng nhập hoặc nhấn phím `Enter` để đăng nhập vào ứng dụng

![App Screenshot](https://i.ibb.co/7j0sCzX/Screenshot-2024-04-19-135543.png)

### Đăng ký

- Tại màn hình đăng nhập, người dùng click vào `Đăng ký` để chuyển đến màn hình đăng ký tài khoản mới

![App Screenshot](https://i.ibb.co/s5jtN9D/Screenshot-2024-04-19-140443.png)

![App Screenshot](https://i.ibb.co/9T0m9Pj/Screenshot-2024-04-19-140627.png)

- Tại màn hình `Đăng ký` để trở lại màn hình `Đăng nhập` người dùng Click chuột vào `Đăng nhập`,

![App Screenshot](https://i.ibb.co/GcsVkmz/Screenshot-2024-04-19-141248.png)
- Tại màn hình đăng ký, người dùng nhập thông tin vào các trường `Tên tài khoản`, `Mật khẩu` và `Nhập lại mật khẩu`, ứng dụng sẽ thực hiện so khớp trường này theo các rằng buộc đã thiết lập sẵn và hiển thị thông báo cho người dùng nếu các trường này không thỏa mãn các ràng buộc

![App Screenshot](https://i.ibb.co/TmYPW1s/Screenshot-2024-04-19-142741.png)

![App Screenshot](https://i.ibb.co/nj7qZJY/Screenshot-2024-04-19-142910.png)

![App Screenshot](https://i.ibb.co/1M4rzPF/Screenshot-2024-04-19-143113.png)

![App Screenshot](https://i.ibb.co/PjPKjxF/Screenshot-2024-04-19-143521.png)

![App Screenshot](https://i.ibb.co/QFxJHdH/Screenshot-2024-04-19-143637.png)

- Sau khi nhập các trường thông tin hợp lệ người dùng Click vào nút `Đăng nhập` hoặc nhấn phím `Enter` để tạo tài khoản mới, sau khi tạo thành công tài khoản mới ứng dụng sẽ chuyển hướng người dùng trở lại màn hình `Đăng nhập`

- Tại màn hình `Đăng nhập`, khi người dùng `Đăng nhập` vào tài khoản vừa tạo,  ứng dụng sẽ hiện thông báo lỗi do tài khoản mới này mặc định chưa có quyền truy cập vào ứng dụng, tài khoản này chỉ có thể truy cập vào ứng dụng khi và chỉ khi admin cấp quyền truy cập cho tài khoản này.

![App Screenshot](https://i.ibb.co/nCJ4Yr7/Screenshot-2024-04-19-145625.png)

### Quản lý tài khoản

- Tại màn hình `Đăng nhập`, người dùng đăng nhập vào tài khoản admin

- Tên tài khoản: `admin`

- Mật khẩu: `admin123`

- Sau khi `Đăng nhập` thành công ứng dụng sẽ chuyển hướng người dùng đến màn hình chính

![App Screenshot](https://i.ibb.co/r6Zg7Qb/Screenshot-2024-04-19-150539.png)

- Tại màn hình chính của ứng dụng, nhấn nút `Tài khoản` để chuyển hướng đến trang `Quản lý tài khoản`

![App Screenshot](https://i.ibb.co/YRySX6z/Screenshot-2024-04-19-150946.png)

- Tại trang `Quản lý tài khoản` người dùng chọn tài khoản cần chỉnh sửa quyền và tiến hành chỉnh sửa

- Tài khoản `admin` là tài khoản `root` của ứng dụng và không thể chỉnh sửa thông tin tài khoản này, đối với các tài khoản khác người dùng `admin` chỉ có thể chỉnh sửa quyền và không thể thay đổi các thông tin đăng nhập của các tài khoản này.

![App Screenshot](https://i.ibb.co/bJJcJDy/Screenshot-2024-04-19-151317.png)

![App Screenshot](https://i.ibb.co/DfNb87V/Screenshot-2024-04-19-151432.png)

- Ngoài ra người dùng `admin` có thể lọc các tài khoản trong hệ thống qua các trường `Tên tài khoản`, `Ngày tạo`, `Được phép đọc` và `Được phép ghi`.

![App Screenshot](https://i.ibb.co/drtQX1R/Screenshot-2024-04-19-153023.png)

- Phân cấp tài khoản trong ứng dụng

#### Tài khoản `admin` : 

- Thêm, sửa, xóa, xem sản phẩm dưới dạng bảng và xem chi tiết

- Sắp xếp, tìm kiếm tài khoản theo các trường thông tin của sản phẩm

- Thống kê, biểu diễn dữ liệu kho hàng dưới dạng biểu đồ

- Quản lý các tài khoản người dùng trong hệ thống

![App Screenshot](https://i.ibb.co/r6Zg7Qb/Screenshot-2024-04-19-150539.png)

#### Tài khoản `đọc và ghi` :

- Thêm, sửa, xóa, xem sản phẩm dưới dạng bảng và xem chi tiết

- Sắp xếp, tìm kiếm tài khoản theo các trường thông tin của sản phẩm

- Thống kê, biểu diễn dữ liệu kho hàng dưới dạng biểu đồ

![App Screenshot](https://i.ibb.co/1Z8DMGt/Screenshot-2024-04-19-160629.png)

#### Tài khoản `chỉ đọc` :

- Xem sản phẩm dưới dạng bảng và xem chi tiết

- Sắp xếp, tìm kiếm tài khoản theo các trường thông tin của sản phẩm

- Thống kê, biểu diễn dữ liệu kho hàng dưới dạng biểu đồ

![App Screenshot](https://i.ibb.co/8DyVL0D/Screenshot-2024-04-19-161420.png)