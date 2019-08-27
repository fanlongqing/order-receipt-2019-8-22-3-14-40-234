package org.katas.refactoring;

/**
 * OrderReceipt prints the details of order including customer name, address, description, quantity,
 * price and amount. It also calculates the sales tax @ 10% and prints as part
 * of order. It computes the total order amount (amount of individual lineItems +
 * total sales tax) and prints it.
 */
public class OrderReceipt {
    private Order o;

    public OrderReceipt(Order o) {
        this.o = o;
    }

    public String printReceipt() {
        StringBuilder output = new StringBuilder();

        // print headers
        printHeaders(output);

        // print date, bill no, customer name
//        output.append("Date - " + order.getDate();

        dataBillName(output);
//        output.append(order.getCustomerLoyaltyNumber());

        // prints lineItems
        LineItems lineItems = new LineItems(output).invoke();
        double totSalesTx = lineItems.getTotSalesTx();
        double tot = lineItems.getTot();
        taxAmount(output, totSalesTx, tot);

        return output.toString();
    }

    private void taxAmount(StringBuilder output, double totSalesTx, double tot) {
        // prints the state tax
        output.append("Sales Tax").append('\t').append(totSalesTx);

        // print total amount
        output.append("Total Amount").append('\t').append(tot);
    }

    private void dataBillName(StringBuilder output) {
        output.append(o.getCustomerName());
        output.append(o.getCustomerAddress());
    }

    private void printHeaders(StringBuilder output) {
        output.append("======Printing Orders======\n");
    }

    public class LineItems {
        private StringBuilder output;
        private double totSalesTx;
        private double tot;

        public LineItems(StringBuilder output) {
            this.output = output;
        }

        public double getTotSalesTx() {
            return totSalesTx;
        }

        public double getTot() {
            return tot;
        }

        public LineItems invoke() {
            totSalesTx = 0d;
            tot = 0d;
            for (LineItem lineItem : o.getLineItems()) {
                output.append(lineItem.getDescription());
                output.append('\t');
                output.append(lineItem.getPrice());
                output.append('\t');
                output.append(lineItem.getQuantity());
                output.append('\t');
                output.append(lineItem.totalAmount());
                output.append('\n');

                // calculate sales tax @ rate of 10%
                double salesTax = lineItem.totalAmount() * .10;
                totSalesTx += salesTax;

                // calculate total amount of lineItem = price * quantity + 10 % sales tax
                tot += lineItem.totalAmount() + salesTax;
            }
            return this;
        }
    }
}