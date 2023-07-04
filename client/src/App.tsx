import React from 'react';
import { Container, Typography } from '@mui/material';
import useFetch from './hooks/useFetch';
import Form from './components/Form';
import List from './components/List';
import { BLOCK_API_ADDRESS, BOOKING_API_ADDRESS } from './constants/constants';
import "./App.css"
const App: React.FC = () => {
  const bookingEndpoint = BOOKING_API_ADDRESS; // Adjust the endpoints accordingly
  const blockEndpoint = BLOCK_API_ADDRESS;

  const {
    data: bookingData,
    loading: bookingLoading,
    error: bookingError,
    refetch: bookingRefetch
  } = useFetch<Array<{
    id: number;
    startDate: string;
    endDate: string;
  }>>(bookingEndpoint);

  const {
    data: blockData,
    loading: blockLoading,
    error: blockError,
    refetch: blockRefetch
  } = useFetch<Array<{
    id: number;
    startDate: string;
    endDate: string;
  }>>(blockEndpoint);

  return (
      <Container maxWidth="md">
        <header>
          <img src="https://www.hostfully.com/wp-content/uploads/2022/08/logo-1.svg" alt="Hostfully Logo" />
        </header>
        <hr/>
        <Typography variant="h4" align="center" gutterBottom>
          Bookings
        </Typography>
        <hr/>
        <Form endpoint={bookingEndpoint} refetch={bookingRefetch}/>
        <List data={bookingData} loading={bookingLoading} error={bookingError} refetch={bookingRefetch} endpoint={bookingEndpoint}/>
        <hr/>
        <Typography variant="h4" align="center" gutterBottom>
          Blocks
        </Typography>
        <hr/>
        <Form endpoint={blockEndpoint} refetch={blockRefetch}/>
        <List data={blockData} loading={blockLoading} error={blockError} refetch={blockRefetch} endpoint={blockEndpoint}/>
      </Container>
  );
};

export default App;
