INSERT INTO `shop_product` (`id`, `product_name`, `model_name`, `price`, `discount_price`, `display`, `mobile_description`, `payment_info`, `shipping_info`, `exchange_info`, `service_info`, `selling`, `simple_description`, `summary_description`, `list_image`, `detail_image`, `has_option`, `sold_out`, `idx`, `last_updated_date`, `created_date`)
VALUES
	('1', '요가 티셔츠', 'YOGATSHIRTS', '80000', '50000', 'Y', '모바일 티셔츠 설명', '결제 정보', '배송 정보', '환불 정보', '서비스 정보', 'Y', '간단한 설명', '종합적인 설명', 'https://img.nabusi.com/2025/12/23/302690048_CY17041.jpg', '[\"https://img.nabusi.com/2025/12/23/302690048_CY17041.jpg\"]', 'Y', 'N', '2', '2025-12-23 13:44:13.538118', '2025-10-22 20:24:11.221758');

INSERT INTO `shop_product_variant` (`id`, `shop_product_id`, `option_name`, `additional_price`, `display`, `selling`, `display_sold_out`, `quantity`, `last_updated_date`, `created_date`)
VALUES
	('1', '1', 'Large', '0', '1', '1', '0', '10', '2025-11-27 21:49:01.917470', '2025-11-27 21:48:50.871296'),
	('2', '1', 'Medium', '0', '1', '1', '0', '20', '2025-11-27 21:50:23.640220', '2025-11-27 21:50:17.510182');
