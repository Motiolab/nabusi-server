package com.motiolab.nabusi_server.centerPackage.center.application;

import com.motiolab.nabusi_server.centerPackage.center.application.dto.CenterMobileDto;

import java.util.List;

public interface CenterMobileService {
    List<CenterMobileDto> getMobileCenterActiveList();
}
