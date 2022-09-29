package com.peigo.challenge.transfer.application.dto.response;

import com.peigo.challenge.accounts.application.dto.response.AccountList;
import com.peigo.challenge.transfer.domain.model.Transfer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class TransferResponse {
    Transfer transfer;

    HttpStatus httpStatus;

}
