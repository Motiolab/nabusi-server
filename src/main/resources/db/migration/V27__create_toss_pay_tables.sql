CREATE TABLE IF NOT EXISTS toss_pay_failure (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(255),
    message VARCHAR(500)
);

CREATE TABLE IF NOT EXISTS toss_pay_easy_pay (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    provider VARCHAR(255),
    amount INT,
    discount_amount INT
);

CREATE TABLE IF NOT EXISTS toss_pay_card (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    issuer_code VARCHAR(50),
    acquirer_code VARCHAR(50),
    number VARCHAR(255),
    installment_plan_months INT,
    is_interest_free BOOLEAN,
    interest_payer VARCHAR(255),
    approve_no VARCHAR(50),
    use_card_point BOOLEAN,
    card_type VARCHAR(50),
    owner_type VARCHAR(50),
    acquire_status VARCHAR(50),
    receipt_url VARCHAR(500),
    amount INT
);

CREATE TABLE IF NOT EXISTS toss_pay (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT,
    wellness_lecture_id BIGINT,
    m_id VARCHAR(255),
    last_transaction_key VARCHAR(255),
    payment_key VARCHAR(255),
    order_id VARCHAR(255),
    order_name VARCHAR(255),
    tax_exemption_amount INT,
    status VARCHAR(50),
    requested_at VARCHAR(50),
    approved_at VARCHAR(50),
    use_escrow BOOLEAN,
    culture_expense BOOLEAN,
    toss_pay_card_id BIGINT UNIQUE,
    toss_pay_cancel_id_list VARCHAR(255) DEFAULT NULL,
    type VARCHAR(50),
    toss_pay_easy_pay_id BIGINT UNIQUE,
    country VARCHAR(10),
    toss_pay_failure_id BIGINT UNIQUE,
    is_partial_cancelable BOOLEAN,
    receipt_url VARCHAR(500),
    checkout_url VARCHAR(500),
    currency VARCHAR(10),
    total_amount INT,
    balance_amount INT,
    supplied_amount INT,
    vat INT,
    tax_free_amount INT,
    method VARCHAR(50),
    version VARCHAR(50),
    last_updated_date DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    created_date DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
);

CREATE TABLE IF NOT EXISTS toss_pay_cancel (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    toss_pay_id BIGINT,
    transaction_key VARCHAR(255),
    cancel_reason VARCHAR(500),
    tax_exemption_amount INT,
    canceled_at VARCHAR(50),
    transfer_discount_amount INT,
    easy_pay_discount_amount INT,
    receipt_key VARCHAR(255),
    cancel_amount INT,
    tax_free_amount INT,
    refundable_amount INT,
    cancel_status VARCHAR(50),
    cancel_request_id VARCHAR(255)
);