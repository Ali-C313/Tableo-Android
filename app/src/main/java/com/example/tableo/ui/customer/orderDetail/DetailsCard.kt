package com.example.tableo.ui.customer.orderDetail

import com.example.tableo.R
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tableo.data.enums.OrderStatus
import com.example.tableo.data.enums.OrderType
import com.example.tableo.data.model.Order
import com.example.tableo.util.estimatedReadyTimeText
import com.example.tableo.util.firstName
import com.example.tableo.data.enums.label
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.tableo.ui.theme.TableoTheme
import com.example.tableo.data.model.OrderItem
import com.example.tableo.data.enums.PaymentMethod

/** Bestellart, Adresse bzw. Tisch, Bezahlart, Fahrer und Fertigzeit. */
@Composable
fun DetailsCard(order: Order) {
    val typeText = when (order.orderType) {
        OrderType.DINE_IN -> stringResource(R.string.order_detail_type_dine_in)
        OrderType.PICKUP -> stringResource(R.string.delivery_mode_pickup)
        OrderType.DELIVERY -> stringResource(R.string.order_type_delivery)
    }
    val placeText = when (order.orderType) {
        OrderType.DINE_IN -> stringResource(R.string.order_type_table, order.tableNumber.toString())
        OrderType.PICKUP -> stringResource(R.string.order_type_pickup_at, order.pickupTime.toString())
        OrderType.DELIVERY -> order.deliveryAddress.orEmpty()
    }

    DetailCard(modifier = Modifier.padding(top = 12.dp)) {
        DetailRow(stringResource(R.string.order_detail_label_type), typeText)
        DetailRow(
            if (order.orderType == OrderType.DELIVERY) stringResource(R.string.order_detail_label_address)
            else stringResource(R.string.order_detail_label_where_when),
            placeText
        )
        DetailRow(stringResource(R.string.order_detail_label_payment), order.paymentMethod.label)
        DetailRow(stringResource(R.string.order_detail_label_ordered_as), order.customerName.ifBlank { stringResource(R.string.common_guest) })
        order.driverName?.let { DetailRow(stringResource(R.string.order_detail_label_driver), it.firstName()) }
        if (!order.status.isDone) {
            DetailRow(stringResource(R.string.order_detail_label_estimated), order.estimatedReadyTimeText())
        }
        if (order.status == OrderStatus.STORNIERT) {
            DetailRow(
                stringResource(R.string.order_detail_label_cancelled_by),
                if (order.cancelledByRestaurant) stringResource(R.string.order_detail_cancelled_by_restaurant)
                else stringResource(R.string.order_detail_cancelled_by_you)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailsCardPreview() {
    TableoTheme {
        DetailsCard(order = Order(
            id = "a1b2c3d4",
            restaurantId = "1",
            restaurantName = "Layali Lounge",
            customerName = "Sara",
            orderType = OrderType.DELIVERY,
            deliveryAddress = "Schanzenstraße 5, 20357 Hamburg",
            driverName = "Omar Haddad",
            paymentMethod = PaymentMethod.PAYPAL,
            items = listOf(OrderItem(menuItemId = "1", name = "Falafel-Teller", pricePerItem = 11.5, quantity = 2)),
            status = OrderStatus.UNTERWEGS
        ))
    }
}
