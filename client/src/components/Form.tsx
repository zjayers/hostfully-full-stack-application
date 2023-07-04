import React, { useState, FormEvent } from 'react';
import { Box, Button, TextField, Snackbar, Card, CardContent } from '@mui/material';
import { Alert } from '@mui/material';
import { createBlockOrBooking, deleteBlockOrBooking, updateBlockOrBooking } from '../helpers/api';
import "./Form.scss"

interface FormProps {
  endpoint: string;
  refetch: () => void;
}

const Form: React.FC<FormProps> = ({ endpoint, refetch }) => {
  const currentDate = new Date().toISOString().split('T')[0];
  const nextDay = new Date();
  nextDay.setDate(nextDay.getDate() + 1);
  const formattedNextDay = nextDay.toISOString().split('T')[0];

  const [id, setId] = useState('');
  const [startDate, setStartDate] = useState(currentDate);
  const [endDate, setEndDate] = useState(formattedNextDay);
  const [open, setOpen] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');

  const submitForm = async (event: FormEvent) => {
    event.preventDefault();

    if (!startDate || !endDate) {
      throw new Error('Please provide both start and end dates.');
    }

    try {
      if (id) {
        await updateBlockOrBooking(endpoint, id, { startDate, endDate });
      } else {
        await createBlockOrBooking(endpoint, { startDate, endDate });
      }
      refetch();
    } catch (error) {
      setErrorMessage((error as Error).message);
      setOpen(true);
    }
  };

  const deleteItem = async () => {
    try {
      if (id) {
        await deleteBlockOrBooking(endpoint, id);
      }
      refetch();
    } catch (error) {
      setErrorMessage((error as Error).message);
      setOpen(true);
    }
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
      <Box className="form-container">
        <Card className="card">
          <CardContent>
            <form onSubmit={submitForm}>
              <TextField label="ID (Optional)" variant="outlined" value={id} onChange={e => setId(e.target.value)} fullWidth/>
              <hr/>
              <TextField label="Start Date" InputLabelProps={{ shrink: true, required: true  }} type="date" variant="outlined" value={startDate} onChange={e => setStartDate(e.target.value)} fullWidth/>
              <hr/>
              <TextField label="End Date"  InputLabelProps={{ shrink: true, required: true }} type="date" variant="outlined" value={endDate} onChange={e => setEndDate(e.target.value)} fullWidth/>
              <Box className="button-box">
                <Button type="submit" variant="contained" color="primary" className="form-button">{id ? "Update" : "Submit"}</Button>
                { id && <Button type="button" onClick={deleteItem} variant="contained" color="secondary" className="form-button">Delete</Button> }
              </Box>
            </form>
          </CardContent>
        </Card>
        <Snackbar open={open} autoHideDuration={6000} onClose={handleClose}>
          <Alert severity="error" onClose={handleClose}>
            {errorMessage}
          </Alert>
        </Snackbar>
      </Box>
  );
};

export default Form;
