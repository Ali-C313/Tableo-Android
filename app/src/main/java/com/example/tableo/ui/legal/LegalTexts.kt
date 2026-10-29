package com.example.tableo.ui.legal

import com.example.tableo.R
import androidx.compose.runtime.Composable
import com.example.tableo.data.enums.LegalPage
import androidx.compose.ui.res.stringResource

/**
 * Texte für Impressum, Datenschutz und AGB.
 * Platzhalter in [eckigen Klammern] vor der Veröffentlichung mit echten Daten füllen.
 */
@Composable
fun legalContent(page: LegalPage): LegalContent = when (page) {
    LegalPage.IMPRESSUM -> LegalContent(
        title = stringResource(R.string.legal_imprint_title),
        sections = listOf(
            stringResource(R.string.legal_imprint_info_heading) to stringResource(R.string.legal_imprint_info_text),
            stringResource(R.string.legal_imprint_contact_heading) to stringResource(R.string.legal_imprint_contact_text),
            stringResource(R.string.legal_imprint_responsible_heading) to stringResource(R.string.legal_imprint_responsible_text),
            stringResource(R.string.legal_imprint_note_heading) to stringResource(R.string.legal_imprint_note_text),
            stringResource(R.string.legal_imprint_dispute_heading) to stringResource(R.string.legal_imprint_dispute_text)
        )
    )

    LegalPage.DATENSCHUTZ -> LegalContent(
        title = stringResource(R.string.legal_privacy_title),
        sections = listOf(
            stringResource(R.string.legal_privacy_controller_heading) to stringResource(R.string.legal_privacy_controller_text),
            stringResource(R.string.legal_privacy_data_heading) to stringResource(R.string.legal_privacy_data_text),
            stringResource(R.string.legal_privacy_location_heading) to stringResource(R.string.legal_privacy_location_text),
            stringResource(R.string.legal_privacy_staff_heading) to stringResource(R.string.legal_privacy_staff_text),
            stringResource(R.string.legal_privacy_services_heading) to stringResource(R.string.legal_privacy_services_text),
            stringResource(R.string.legal_privacy_device_heading) to stringResource(R.string.legal_privacy_device_text),
            stringResource(R.string.legal_privacy_notifications_heading) to stringResource(R.string.legal_privacy_notifications_text),
            stringResource(R.string.legal_privacy_deletion_heading) to stringResource(R.string.legal_privacy_deletion_text),
            stringResource(R.string.legal_privacy_rights_heading) to stringResource(R.string.legal_privacy_rights_text)
        )
    )

    LegalPage.AGB -> LegalContent(
        title = stringResource(R.string.legal_terms_title),
        sections = listOf(
            stringResource(R.string.legal_terms_scope_heading) to stringResource(R.string.legal_terms_scope_text),
            stringResource(R.string.legal_terms_parties_heading) to stringResource(R.string.legal_terms_parties_text),
            stringResource(R.string.legal_terms_order_heading) to stringResource(R.string.legal_terms_order_text),
            stringResource(R.string.legal_terms_prices_heading) to stringResource(R.string.legal_terms_prices_text),
            stringResource(R.string.legal_terms_delivery_heading) to stringResource(R.string.legal_terms_delivery_text),
            stringResource(R.string.legal_terms_account_heading) to stringResource(R.string.legal_terms_account_text),
            stringResource(R.string.legal_terms_liability_heading) to stringResource(R.string.legal_terms_liability_text),
            stringResource(R.string.legal_terms_final_heading) to stringResource(R.string.legal_terms_final_text)
        )
    )
}
